package ch.protonmail.vladyslavbond.eurobug.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public final class PasswordTest 
extends Object 
{
    public PasswordTest ( )
    {
    	this.correctPassword = "correct";
        this.wrongPassword   = "wrong";
    }

    private final String correctPassword;
    private final String wrongPassword;
    
    @Test
    public void testInstantiation ( ) 
    {
		Password password = new Password (this.correctPassword);
        assertFalse(password.toString( ).equals(this.correctPassword));
        assertTrue(password.equals(this.correctPassword));
        assertFalse(password.equals(this.wrongPassword));
    }
}
