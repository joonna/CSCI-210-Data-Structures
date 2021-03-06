/**
 * CPU class, keeps track of what process is in the cpu at the moment, 
 * what level and other job properties that are running
 *@author Jonathan Kohler
 *@version 7/22/19
 *
 */
public class CPU {

    private int job;
    //keeps track of time quanta remaining for current job on CPU
    private int cpuQuantumClock;
    private  boolean busyFlag;
    private int currentLevel;
    private int time_remaining_to_complete;
    private int job_start_time;
    private int cpu_time;
    private JFrameExtraCredit gui;

    /**
     * Class constructor for CPU 
     * @param gui
     */
    public CPU( JFrameExtraCredit gui) {
        this.job = 0;
        this.cpuQuantumClock = 0;
        this.currentLevel = 0;
        this.busyFlag = false;
        this.time_remaining_to_complete = 0;
        this.job_start_time = 0;
        this.cpu_time = 0;
        this.gui = gui;
        this.gui.setBusyFlag_GUI(false);
    }

    /**
     * Changes the status of the CPU when a new job arrives
     * 
     * @param jobID
     * @param time_remaining
     * @param cpuQuantumClock
     * @param arriveTime
     * @param CPU_time
     */

    public void newJob(int jobID, int time_remaining, int cpuQuantumClock, int arriveTime, int CPU_time)
    {
        this.job = jobID;
        this.cpuQuantumClock = cpuQuantumClock;
        this.currentLevel = 1; // Every new job starts in queue 1
        this.busyFlag = true;
        this.time_remaining_to_complete = time_remaining;
        this.job_start_time = arriveTime;
        this.cpu_time = CPU_time;

    }

    /**
     * Edits Quantum clock for each second
     * 
     * @return false if the current job finished and changes the busyFlag
     */ 
    public boolean editQuantumClock() {
        //If there are any jobs running, change the Quantum clock and the current's job total time in the system
        if (this.cpuQuantumClock > 0) {
            this.cpuQuantumClock = this.cpuQuantumClock - 1;
            this.time_remaining_to_complete = this.time_remaining_to_complete - 1; 
            gui.quantum.setText("Quantum Time: " + cpuQuantumClock);
            gui.job.setText("Job Time: " + time_remaining_to_complete);
        }
        gui.quantum.setText("Quantum Time: " + cpuQuantumClock);
        gui.job.setText("Job Time: " + time_remaining_to_complete);
        //If the current job finished, change the flag to false
        if (this.cpuQuantumClock == 0) {
            this.busyFlag = false;
        }
        return this.busyFlag;

    }

    /**
     * Starts the same job running at the next level
     * @param level
     * @param movedJob
     */
    public void start_next_level(int level, Job movedJob) {

        this.job = movedJob.getPID();
        this.cpuQuantumClock = getQuantum_forLevel(level);
        this.currentLevel = level;
        this.busyFlag = true;
        this.time_remaining_to_complete = movedJob.getTimeRemaining();
        this.job_start_time = movedJob.getArrivalTime();
        this.cpu_time = movedJob.getCpuTimeRequired();
    }

    /**
     * Extracts the quantum time for different level
     * @param level
     * @return quantum
     */
    public int getQuantum_forLevel(int level) {
        int quantum = 0;
        if (level == 1) {
            quantum = 1;
        } else if (level == 2) {
            quantum =4;
        } else if (level == 3) {
            quantum = 8;
        } else {
            quantum = 16;
        }
        return quantum;
    }

    /**
     * Returns the arrival time of the job
     * @return arrival time of the job
     */
    public int get_arrival_time() {
        return this.job_start_time;
    }

    /**
     * Return if the CPU is busy at the moment
     * @return busyFlag
     */
    public boolean getFlag() {
        return this.busyFlag;
    }

    /**
     * Sets a new cpu level
     * @param lev
     */
    public void setLevel(int lev) {
        this.currentLevel = lev;
    }

    /**
     * If the CPU is busy returns the level
     * @return currentLevel
     */
    public int getLevel() {
        return this.currentLevel;
    }

    /**
     * Returns quantum clock time
     * @return QuantumClock
     */
    public int getQuantumClock() {
        return this.cpuQuantumClock;
    }

    /**
     * Returns the ID of the running job
     * @return job
     */
    public int getPID() {
        return this.job;
    }

    /**
     *  Returns the time needed for the current job to finish
     *  @return time_remaining_to_complete
     */
    public int get_remaining_time(){
        return this.time_remaining_to_complete;
    }

    /**
     * Returns the CPU time needed
     * @return cpu_time
     */
    public int getCPUtime() {
        return this.cpu_time;
    }
}