package bowling.game.frame;

import bowling.game.Score;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Frames {
    private final LinkedList<Frame> frames;

    public Frames() {
        this.frames = new LinkedList<>();
        this.frames.add(new NormalFrame());
    }

    public Frame getCurrentFrame() {
        return frames.getLast();
    }

    public void createNextFrame() {
        int currentFrameNumber = frames.size();

        Frame nextFrame = getCurrentFrame().createNextFrame(currentFrameNumber + 1);

        frames.add(nextFrame);
    }

    public void bowlCurrentFrame(final int pinCount) {
        Frame current = getCurrentFrame();
        current.bowl(pinCount);

        if (!hasRemainChance() && current instanceof NormalFrame) {
            createNextFrame();
        }
    }

    public boolean hasRemainChance() {
        return getCurrentFrame().hasRemainChance();
    }

    public boolean isEndAllFrames() {
        Frame frame = getCurrentFrame();

        return frame.isLastFrame() && !frame.hasRemainChance();
    }

    public int getCurrentFrameNumber() {
        return frames.size();
    }

    public List<String> getFramesStates() {
        return frames.stream()
                .map(Frame::getStates)
                .collect(Collectors.toList());
    }

    public List<Score> getScores() {
        return frames.stream()
                .map(Frame::calculateScore)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
