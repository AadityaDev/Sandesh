package com.technawabs.sandesh.pojo;

public class Sms {

    /**
     * The thread ID of the message.
     * <P>Type: INTEGER</P>
     */
    private String _ID;

    /**
     * The address of the other party.
     * <P>Type: TEXT</P>
     */
    private String ADDRESS;

    /**
     * The date the message was received.
     * <P>Type: INTEGER (long)</P>
     */
    private String DATE;

    /**
     * The date the message was sent.
     * <P>Type: INTEGER (long)</P>
     */
    private String DATE_SENT;

    /**
     * The subject of the message, if present.
     * <P>Type: TEXT</P>
     */
    private String SUBJECT;

    /**
     * The body of the message.
     * <P>Type: TEXT</P>
     */
    private String BODY;

    /**
     * The ID of the sender of the conversation, if present.
     * <P>Type: INTEGER (reference to item in {@code content://contacts/people})</P>
     */
    private String PERSON;

    /**
     * Is the {@code TP-Reply-Path} flag set?
     * <P>Type: BOOLEAN</P>
     */
    private String REPLY_PATH_PRESENT;

    /**
     * Error code associated with sending or receiving this message
     * <P>Type: INTEGER</P>
     */
    private String ERROR_CODE;

    private String IMAGE_URI;

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getDATE_SENT() {
        return DATE_SENT;
    }

    public void setDATE_SENT(String DATE_SENT) {
        this.DATE_SENT = DATE_SENT;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getBODY() {
        return BODY;
    }

    public void setBODY(String BODY) {
        this.BODY = BODY;
    }

    public String getPERSON() {
        return PERSON;
    }

    public void setPERSON(String PERSON) {
        this.PERSON = PERSON;
    }

    public String getREPLY_PATH_PRESENT() {
        return REPLY_PATH_PRESENT;
    }

    public void setREPLY_PATH_PRESENT(String REPLY_PATH_PRESENT) {
        this.REPLY_PATH_PRESENT = REPLY_PATH_PRESENT;
    }

    public String getERROR_CODE() {
        return ERROR_CODE;
    }

    public void setERROR_CODE(String ERROR_CODE) {
        this.ERROR_CODE = ERROR_CODE;
    }

    public String getIMAGE_URI() {
        return IMAGE_URI;
    }

    public void setIMAGE_URI(String IMAGE_URI) {
        this.IMAGE_URI = IMAGE_URI;
    }
}
