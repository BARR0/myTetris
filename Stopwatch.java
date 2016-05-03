/******************************************************************************
 *  Compilation:  javac Stopwatch.java
 *  Execution:    java Stopwatch n
 *  Dependencies: none
 *
 *  A utility class to measure the running time (wall clock) of a program.
 *
 *  % java8 Stopwatch 100000000
 *  6.666667e+11  0.5820 seconds
 *  6.666667e+11  8.4530 seconds
 *
 ******************************************************************************/

/**
 *  The <tt>Stopwatch</tt> data type is for measuring
 *  the time that elapses between the start and end of a
 *  programming task (wall-clock time).
 *
 *  See {@link StopwatchCPU} for a version that measures CPU time.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
//modificado

public class Stopwatch{ 
    private long start;

    public Stopwatch() {
        this.start=-1;
    }
    public boolean isOn(){
    	return this.start!=-1;
    }
    public long elapsedTime(){
        if(start!=-1)return (System.currentTimeMillis()-this.start);
        return 0;
    }
    public void start(){
    	this.start=System.currentTimeMillis();
    }
    public void stop(){
    	this.start=-1;
    }
} 
