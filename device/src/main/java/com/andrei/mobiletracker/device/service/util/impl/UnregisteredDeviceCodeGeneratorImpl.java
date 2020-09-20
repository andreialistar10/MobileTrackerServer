package com.andrei.mobiletracker.device.service.util.impl;

import com.andrei.mobiletracker.device.dao.deviceconstant.DeviceConstantDao;
import com.andrei.mobiletracker.device.model.DeviceConstant;
import com.andrei.mobiletracker.device.model.DeviceConstantName;
import com.andrei.mobiletracker.device.service.util.UnregisteredDeviceCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class UnregisteredDeviceCodeGeneratorImpl implements UnregisteredDeviceCodeGenerator {

    @Autowired
    private DeviceConstantDao deviceConstantDao;

    private final DecimalFormat decimalFormat;

    private static final int MAX_BODY_LENGTH = 12;

    private static final String header = "MOTR";

    private static final String DELIMITER = "_";


    public UnregisteredDeviceCodeGeneratorImpl() {

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        this.decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    @Override
    public String generate(String name) {

        DeviceConstant deviceConstant = deviceConstantDao.findOneByDeviceConstantName(DeviceConstantName.UNREGISTERED_DEVICE_INDEX);
        Long currentId = deviceConstant.getValue();
        String pattern = this.getPattern(currentId);
        decimalFormat.applyLocalizedPattern(pattern);
        String footer = decimalFormat.format(currentId);

        String body = getBody(name, MAX_BODY_LENGTH - footer.length());
        StringBuilder code = new StringBuilder(header)
                .append(DELIMITER)
                .append(body)
                .append(DELIMITER)
                .append(footer);

        deviceConstant.setValue(currentId + 1);
        deviceConstantDao.updateOneDeviceConstant(deviceConstant);
        return code.toString();
    }

    private String getBody(String name, int length) {

        String hashString = String.valueOf(name.hashCode());
        if (hashString.length() > length){
            return hashString.substring(hashString.length() - length);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int zerosDigits = length - hashString.length();
        for (int i = 0; i< zerosDigits; ++i){
            stringBuilder.append("0");
        }
        stringBuilder.append(hashString);
        return stringBuilder.toString();
    }

    private String getPattern(Long currentId) {

        StringBuilder stringBuilder = new StringBuilder();
        if (currentId < 0) {
            stringBuilder.append("-");
            currentId = currentId * (-1);
        }
        LongRange longRange = LongRange.getValue(currentId);
        stringBuilder.append(longRange.pattern);
        return stringBuilder.toString();
    }

    private enum LongRange {

        NEGATIVE("-"),
        LESS_THAN_10_000("0000"),
        GREATER_THAN_9_999_AND_LESS_THAN_100_000_000("0000.0000"),
        GREATER_THAN_99_999_999_AND_LESS_THAN_1_000_000_000_000("0000.0000.0000"),
        GREATER_THAN_999_999_999_999_AND_LESS_THAN_10_000_000_000_000_000("0000.0000.0000.0000"),
        GREATER_THAN_9_999_999_999_999_999("0000.0000.0000.0000.0000");

        public final String pattern;

        LongRange(String pattern) {
            this.pattern = pattern;
        }

        public static LongRange getValue(long value) {

            if (value < 0) {
                return NEGATIVE;
            }
            if (value < 10_000L) {
                return LESS_THAN_10_000;
            }
            if (value < 100_000_000L) {
                return GREATER_THAN_9_999_AND_LESS_THAN_100_000_000;
            }
            if (value < 1_000_000_000_000L) {
                return GREATER_THAN_99_999_999_AND_LESS_THAN_1_000_000_000_000;
            }
            if (value < 10_000_000_000_000_000L) {
                return GREATER_THAN_999_999_999_999_AND_LESS_THAN_10_000_000_000_000_000;
            }
            return GREATER_THAN_9_999_999_999_999_999;
        }
    }
}
