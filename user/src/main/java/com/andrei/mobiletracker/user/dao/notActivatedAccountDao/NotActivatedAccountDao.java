package com.andrei.mobiletracker.user.dao.notActivatedAccountDao;

import com.andrei.mobiletracker.user.model.NotActivatedAccount;

public interface NotActivatedAccountDao {

    NotActivatedAccount saveOneNotActivatedAccount(NotActivatedAccount notActivatedAccount);

    NotActivatedAccount findOneNotActivatedAccountByToken(String token);
}
