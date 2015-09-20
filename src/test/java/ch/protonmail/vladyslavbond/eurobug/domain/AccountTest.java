package ch.protonmail.vladyslavbond.eurobug.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.LongStream;

import org.junit.Test;

import ch.protonmail.vladyslavbond.eurobug.utils.Identificator;
import ch.protonmail.vladyslavbond.eurobug.utils.NumericIdentificator;

public final class AccountTest
{
    @Test
    public void testCreation ( )
    {
        Random random = new Random ( );
        LongStream longs = random.longs(10000);
        String name = "noname";
        for (Iterator<Long> i = longs.iterator(); i.hasNext( ); )
        {
            Long idValue = i.next( );
            Identificator<Account> id = NumericIdentificator.<Account>valueOf(idValue);
            Account account = new Account (id, name);
            assertEquals(id, account.getId( ));
            assertEquals(name, account.getName( ));
            assertFalse(account.equals(Account.EMPTY) || account == Account.EMPTY);
            assertTrue(Account.EMPTY.equals(Account.EMPTY));
        }
    }
}
