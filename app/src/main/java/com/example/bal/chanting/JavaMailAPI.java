package com.example.bal.chanting;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String mSubject;
    private String mMessage;
    private ProgressDialog mProgressDialog;
    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage)
    {
        this.mContext = mContext;
        this.mSubject = mSubject;
        this.mEmail = mEmail;
        this.mMessage = mMessage;
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "Sending Feedback", "Please wait.....", false, false);
    }
    @Override
    protected void onPostExecute(Void Vvoid)
    {
        super.onPostExecute(Vvoid);
        mProgressDialog.dismiss();
        Toast.makeText(mContext, "Thanks for your Feedback.", Toast.LENGTH_LONG).show();
    }
    @Override
    protected Void doInBackground(Void... params)
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Utils.Email, Utils.Pass);
                    }
                });
        try {
            MimeMessage mm = new MimeMessage(mSession);
            mm.setFrom(new InternetAddress(Utils.Email));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);
            mm.setText(mMessage);
            Transport.send(mm);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
