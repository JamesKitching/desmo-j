package desmoj.extensions.db.visustorage;

import desmoj.extensions.visualEvents.VisualEvent;

import desmoj.extensions.visualEvents.VisualEventTransmitter;

import java.util.TimerTask;

public class EventTask extends TimerTask
{
    
    private VisualEvent _event;
    
    private VisualEventTransmitter _transmitter;
    
    
    public EventTask(VisualEvent visualEvent, VisualEventTransmitter transmitter)
    {
        super();
        _event = visualEvent;
        _transmitter = transmitter;
    }

    public void run()
    {
        _transmitter.fireVisualEvent(_event);
    }
}
