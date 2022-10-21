package engine.tokens;

public class MapToken extends Token {
	public MapToken(String value) {
		super(value);
		type = TokenType.MAP;
	}
}
