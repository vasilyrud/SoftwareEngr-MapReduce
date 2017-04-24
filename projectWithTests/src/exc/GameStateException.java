package exc;

public class GameStateException extends Exception {
    public GameStateException() {
        super();
    }
    
    public GameStateException(String message) {
        super(message);
    }
}
