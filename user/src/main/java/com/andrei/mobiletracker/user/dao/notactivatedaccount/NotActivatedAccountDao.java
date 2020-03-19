package com.andrei.mobiletracker.user.dao.notactivatedaccount;

import com.andrei.mobiletracker.user.model.NotActivatedAccount;

public interface NotActivatedAccountDao {

    NotActivatedAccount saveOneNotActivatedAccount(NotActivatedAccount notActivatedAccount);

    NotActivatedAccount updateOneNotActivatedAccount(NotActivatedAccount notActivatedAccount);

    NotActivatedAccount findOneNotActivatedAccountByToken(String token);

    NotActivatedAccount deleteOneNotActivatedAccount(NotActivatedAccount notActivatedAccount);
}
