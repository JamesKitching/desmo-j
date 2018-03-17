package desmoj.extensions.db.visustorage;

import java.util.TimerTask;

import javax.swing.JFrame;

public class FrameTask extends TimerTask
{
    
    boolean _showFrame;
    
    JFrame _frame;
    
    public FrameTask(JFrame frame, boolean isVisible)
    {
        super();
        _frame = frame;
        _showFrame = isVisible;
    }

    public void run()
    {
        _frame.setVisible(_showFrame);
    }
}
