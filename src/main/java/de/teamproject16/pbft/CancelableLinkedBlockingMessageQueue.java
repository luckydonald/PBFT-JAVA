package de.teamproject16.pbft;


import de.teamproject16.pbft.Messages.Message;

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
public class CancelableLinkedBlockingMessageQueue<T extends Message> extends CancelableLinkedBlockingQueue<T> {
    private static final long serialVersionUID = 0x1L;

    /**
     * May return null if producer ends the production after consumer
     * has entered the element-await state.
     * @param current_sequence_no: it will skip (discard) all omessages with a sequence_no below that.
     *
     * @throws CancellationException when .cancel() was called somewhere.
     */
    public T take(long current_sequence_no) throws InterruptedException, CancellationException {
        T el;
        while (((el = super.poll()) == null || el.sequence_no < current_sequence_no) && !this.isCanceled()) {  // while has no next element and is not canceled.
            synchronized (this) {
                wait();
            }
        }
        if (this.isCanceled()) {
            throw new CancellationException("Done.");
        }
        return el;
    }
}