package engine.tokens;

public class SectionToken extends Token {
	public SectionToken(String value) {
		super(value);
		type = TokenType.SECTION;
	}
}
