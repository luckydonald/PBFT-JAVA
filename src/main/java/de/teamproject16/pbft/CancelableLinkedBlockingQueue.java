package de.teamproject16.pbft;


import java.util.concurrent.CancellationException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * //TODO: Here are many original funcions which don't respect the cancel() command.
 * A class which allows to abort the waiting for new entries by calling {@link #cancel()}.
 * Also has {@link #isCanceled()} and {@link #uncancel()}.
 *
 * Currently only {@link #put(Object)} and {@link #take()} are save to access and use the underlaying {@link LinkedBlockingQueue}.
 *
 * @author luckydonald
 * @since 26.10.2016
 * @see LinkedBlockingQueue
 **/
public class CancelableLinkedBlockingQueue<T> extends LinkedBlockingQueue<T> {
    private static final long serialVersionUID = 1L;
    private boolean canceled = false;

    public CancelableLinkedBlockingQueue() {  super(); }

    /**
     * Cancels any blocking {@link #take()} calls.
     * Any calls to {@link #take()} (blocking or new) will throw a {@link CancellationException}.
     */
    public void cancel() {
        canceled = true;
        synchronized (this) {
            notifyAll();
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void uncancel() {
        canceled = false;
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary for space to become available.
     *
     * @param element Element to append.
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public void put(T element) throws InterruptedException {
        super.put(element);
        synchronized (this) {
            notify();
        }
    }


    /**
     * May return null if producer ends the production after consumer
     * has entered the element-await state.
     *
     * @throws CancellationException when .cancel() was called somewhere.
     */
    public T take() throws InterruptedException, CancellationException {
        T el;
        while ((el = super.poll()) == null && !canceled) {  // while has no next element and is not canceled.
            synchronized (this) {
                wait();
            }
        }
        if (canceled) {
            throw new CancellationException("Done.");
        }
        return el;
    }
}