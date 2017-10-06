package snakelib;

import java.util.EventListener;

public interface SnakeEventListener extends EventListener {
    public void actionPerformed(SnakeGameEvent event);
}
