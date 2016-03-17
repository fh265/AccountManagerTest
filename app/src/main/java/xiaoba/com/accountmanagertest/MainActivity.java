package xiaoba.com.accountmanagertest;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AccountAuthenticatorActivity {
    private static final String TAG = "MainActivity";
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        AccountManager am = AccountManager.get(this);
//        Account[] accounts = am.getAccountsByType(Constants.ACCOUNT_TYPE);
        Account[] accounts = am.getAccounts();


        StringBuilder builder = new StringBuilder();
        for (Account account : accounts) {
            String name = account.name;
            String type = account.type;
            String password = am.getPassword(account);
            String userData = am.getUserData(account, "password");
            builder.append("\n")
                    .append("name=").append(name).append(",")
                    .append("type=").append(type).append(",")
                    .append("password=").append(password).append(",")
                    .append("userData=").append(userData)
                    .append("\n").append("\n");
        }

        text.setText(builder.toString());
    }

    private AccountManager mAccountManager;

    public void addAccount(View view) {
        Log.i(TAG, "finishLogin()");
        mAccountManager = AccountManager.get(this);
        final Account account = new Account("TestAccount", Constants.ACCOUNT_TYPE);

        Bundle userdata = new Bundle();
        userdata.putString("password", "123456789");
        mAccountManager.addAccountExplicitly(account, "testPasword12313", userdata);
        // Set contacts sync for this account.
        ContentResolver.setSyncAutomatically(account, ContactsContract.AUTHORITY, true);

        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, "TestAccount");
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
        intent.putExtra(AccountManager.KEY_PASSWORD, "123456456789");
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }


}
