package com.naive.phase.Auxiliary.Task;

public abstract class ProgressedTask extends AbstractTask {

    protected int progress;
    protected int maxProgress;

    protected ProgressedTask(int progress, int maxProgress, TaskElement out) {
        super(out);
        this.progress = progress;
        this.maxProgress = maxProgress;
    }

    public boolean progress(int value) {
        progress += value;
        return progress >= maxProgress;
    }

    public boolean isDone() {
        return progress >= maxProgress;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public float getProgressPercentage() {
        return progress / (float) maxProgress;
    }

}
