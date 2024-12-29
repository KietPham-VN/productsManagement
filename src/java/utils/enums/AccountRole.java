package utils.enums;

public enum AccountRole
{
    CUSTOMER(0),
    STAFF(1);

    private final int value;

    AccountRole(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
