package dev.librry.utils;

public class ProcessorCallback {
    private boolean forceComputeFrames = false;

    public boolean isForceComputeFrames() {
        return forceComputeFrames;
    }

    public void setForceComputeFrames() {
        this.forceComputeFrames = true;
    }
}