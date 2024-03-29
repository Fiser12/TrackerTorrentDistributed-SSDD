package Tracker.View;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 *
 * @author David Buzatto
 */
public class IntegerField extends JTextField {

    public IntegerField() {
        super();
    }

    public IntegerField(int cols ) {
        super( cols );
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;

            for (char aChar : chars) {

                try {
                    Integer.parseInt(String.valueOf(aChar));
                } catch (NumberFormatException exc) {
                    ok = false;
                    break;
                }


            }

            if ( ok ) {
                super.insertString(offs, new String(chars), a);
            }
        }
    }
    public int getNumber(){
        String numero = getText();
        return Integer.parseInt(numero);
    }

}
