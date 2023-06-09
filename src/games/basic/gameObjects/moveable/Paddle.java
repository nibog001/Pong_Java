package src.games.basic.gameObjects.moveable;

import src.games.basic.gameObjects.RectangularGameObject;
import src.games.basic.position.Position;
import src.games.basic.position.interfaces.Positionable;

import java.awt.*;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Paddle extends AbstractMoveableGameObject implements Runnable {

    private boolean isPressedUp, isPressedDown;
    private int width;
    private int height;

    private Color colour;

    private int score;

    private int strokeSize;


    public Paddle(Positionable pos, int width, int height, Color colour, int strokeSize) {
        // Beachte: jetzt nicht this.pos setzen, sondern super.pos !
        super(pos, new Position(0, 20));        // Aufruf: AbstractGameObject(pos);
        this.score = 0;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.strokeSize = strokeSize;
        this.isPressedUp = false;
        this.isPressedDown = false;
        addListener();
    }

    private void addListener() {
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colour);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.drawRect(this.getPos().getX(), this.getPos().getY(), getWidth(), getHeight());
        g2.fillRect(this.getPos().getX(), this.getPos().getY(), getWidth(), getHeight());
    }

    public String toString() {
        // verwende toString-Methode aus Positionable
        return ("pos = " + this.getPos()
                + ", size = " + this.getWidth() + " x " + this.getHeight() + ")");
    }

    public void setIsPressedUp(boolean isPressed) {
        this.isPressedUp = isPressed;
    }

    public void setIsPressedDown(boolean isPressed) {
        this.isPressedDown = isPressed;
    }

    public boolean isPressedUp() {
        return isPressedUp;
    }

    public boolean isPressedDown() {
        return isPressedDown;
    }


    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof RectangularGameObject))
            return false;

        RectangularGameObject otherRect = (RectangularGameObject) other;
        Positionable thisPos = this.getPos();
        // Beachte: this.pos funktioniert nicht, da Attribut pos in Oberklasse private ist!
        //		    --> getPos() funktioniert, egal ob Attribut pos hier oder in Oberklasse
        //		  	 	definiert ist
        Positionable otherPos = otherRect.getPos();

        return  // vergleiche aktuelle Position
                //		verwende equals-Methode von Positionable
                thisPos.equals(otherPos)
                        // vergleiche Breite/H�he
                        && this.getWidth() == otherRect.getWidth()
                        && this.getHeight() == otherRect.getHeight();
    }


    @Override
    public void run() {
        while (currentThread().isAlive()) {
            this.move();
            if (isPressedUp()) {
                setDeltaPos(new Position(0, -20));
            } else if (isPressedDown()) {
                setDeltaPos(new Position(0, 20));
            } else {
                setDeltaPos(new Position(0, 0));
            }
            try {
                sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

}

