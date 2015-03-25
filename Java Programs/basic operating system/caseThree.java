/*
Jared Starr
CS 350 Term Project
4-8-2014
*/

import java.io.*;
import java.util.*;

public class caseThree
{
	private int timeUnits = 30; //this is how the 'time' amount is simulated
	private int readyJobs = 0; //this is how many jobs are in the ready queue, initially 0
    private int jobCount = 0; //this keeps track of how many jobs are in memory, initially 0
	private int memoryLoss = 0; //this tracks how much memory is wasted for each particular case
    
    private Queue<Job> rQueue = new LinkedList<Job>(); //rQueue represents the ready queue
    private Queue<Job> jQueue = new LinkedList<Job>(); //jQueue represents the job queue
    private Queue<Job> cQueue = new LinkedList<Job>(); //cQueue represents the completed queue, where jobs that are finished are stored
    private BufferedWriter w; 
    
    public caseThree(Job[] job, Segment[] mem)
    {
        jQueue.addAll(Arrays.asList(job)); //fill up the job queue
        int jobs = jQueue.size();
		
        try
        {
            w = new BufferedWriter(new FileWriter("JScaseThreeOut.txt")); //create the output file
            for(int i = 1; i < timeUnits; i++)
            {
                for(int j = 0; j < jobs; j++)
                { //go through the jobs and attempt to assign them to memory segments
                    if(jQueue.peek() != null)
                    { //if job queue isn't empty
                        Job placeHolder = new Job(); //create another placeholder job
                        Job best = new Job(); //create a job that should be the best fit
                        int comparison = 100; //this value will be used to compare set the initial best fit solution, since no job can be this big
                        placeHolder = jQueue.peek();
                        for(int k = 0; k < mem.length; k++)
                        {
                            for(int l = 0; l < jQueue.size(); l++)
                            {
                                placeHolder = jQueue.peek();
                                if((mem[k].getSize() - placeHolder.getSize() < comparison) && (mem[k].getSize() - placeHolder.getSize() >= 0)&&(mem[k].getInUse() == false) && (placeHolder.getQue() == false))
                                {
                                    comparison = mem[k].getSize() - placeHolder.getSize(); //if all of the above is true, this will set the comparison value for future iterations to the size of the memory at place k.
                                    best = placeHolder; //set the best fit to be the value of the placeHolder job
                                }
                                jQueue.add(jQueue.remove());
                            }
                            for(int m = 0; m < jQueue.size(); m++) 
                            {//now that we've got that working, this loop will go through the job queue to find the best fit
                                if(jQueue.peek().equals(best)) 
                                {//this statement will find the best fit in the job queue and will remove it to be placed in memory
                                    best = jQueue.remove();
                                    if(mem[k].getInUse()==false && best.getQue() == false) 
                                    {
										best.setmemSeg(k); //set the best job into the selected memory segment
                                        best.setStatus(1); //set the status of the best job to ready
                                        mem[k].addJob(best);	//add the best job to the memory segment
                                    }
                                }
                                //If it is not found, change the status to "waiting", and move to the back of the queue
                                else 
                                { //this implies that the best fit was not found and moves the placeHolder job back into the waiting status
                                    placeHolder = jQueue.remove();
                                    placeHolder.setStatus(0);
                                    jQueue.add(placeHolder);
                                }
                            }
                            comparison = 100; //reset the comparison so that the next best fit number will compare to it.
                        }
                    }
                }
                for(int n = 0; n < mem.length; n++) 
                {
                    if(mem[n].getInUse()==false)	
                        mem[n].addJob(new Job(0)); //this loop will set all the non-used jobs to zero for quick comparison
                }
				for(int p = 0; p < mem.length; p++) 
				{
					for(int q = 1; q < mem.length; q++)
					{ //loop through the array to change things to the shortest first ordering, don't set q=0 because then you'll get the wrong number
						if(mem[q-1].getJob().getexTime() > mem[q].getJob().getexTime())
						{
							Job temporary = mem[q-1].getJob(); //create a temporary job for ordering
							mem[q-1].addJob(mem[q].getJob());  //set the space -1 to getting the job
							mem[q].addJob(temporary); //now set the current space to be the temporary job
						}
					}
				}
				for(int r = 0; r < mem.length; r++) //fill the ready queue with jobs, just like in the other cases
				{
					if(mem[r].getInUse() == true && mem[r].getJob().getQue() == false && mem[r].getJob().getSize() != 0)
					{
					
						mem[r].getJob().setQue(true);
						mem[r].getJob().setStatus(1);
						rQueue.add(mem[r].getJob());
					}
				}
				if(rQueue.size()<=4)
					readyJobs = rQueue.size();
				else 
					readyJobs = 4; //just like in caseOne, this sets up the available 'processors' that we'll be using

				for(int s = 0; s < readyJobs; s++)
				{ //this will begin execution of all of the processes
				    Job currentJob = rQueue.remove(); //remove the currentJob from the ready queue to 
					currentJob.setStatus(2); //this sets the current jobs status to Running
					currentJob.subtractTime(1); //this will subtract one time unit
				   
					if(currentJob.getRemaining() < 1)
                    { //if the remaining time for a job is 0
						currentJob.setStatus(3); //sets the job to 'Finished'
						mem[currentJob.getmemSeg()].removeJob(); //removes the current job from the memory segment in which is allocated
						currentJob.setmemSeg(-1); //this will take the current job back out of one of the memory units
						cQueue.add(currentJob); //this job will be added to the completed queue
					}
					else 
						rQueue.add(currentJob); //if the time isn't 0, add the current job to the ready queue
				}
				for(int t = 0; t < mem.length; t++)
				{ //loops through all the memory segments
					if(mem[t].getInUse() == false) //if the memory segment is not in use
						memoryLoss += mem[t].getSize(); //add the amount of memory that is not being used to the memoryLoss variable
				}
                
				//create the info panel
				w.write("TIME" + "\t" + "ID" + "\t" + " SEGMENT" + "\t" + " MEM REQUEST" + "\t" + " TIME REM" + "\t\t" + "MESSAGES" + "\n");
				
				if(rQueue.peek() != null) //if something is in the ready queue
					print(rQueue, i, w); //print the ready queue
				if(!jQueue.isEmpty()) //if there is something in the job queue
					print(jQueue, i, w); //print the job queue
				if(!cQueue.isEmpty()) //if there is something in the completed queue
					print(cQueue, i, w); //print the completed queue.  All of these together will print the contents of every queue
               
				w.write("Memory that is not being used: " + memoryLoss + "\n"); //this will show the user how much memory is not being used
                w.write("Number of jobs that are waiting: " + jQueue.size() + "\n\n"); //this will show how many jobs are in the job queue waiting
				memoryLoss = 0;
            }
			w.write("Completed jobs: " + cQueue.size()); //prints out the number of jobs that have been completed.
			w.close(); //closes the output file   
        }
        catch(Exception e){System.out.print(e.toString());}    
    }
    //Function used to write the queue contents to the output file
	private static void print(Queue<Job> output, int timeSeg, BufferedWriter w) //this function will write the contents.
    {
	    if(!output.isEmpty())
        { //if there is anything in the output list
		    int len = output.size(); //the length is equal to the size of the output list
		    Job[] job = output.toArray(new Job[len]); //new job list is set to be the output of the array by making a new Job with the size of the len variable
		    for(int i = 0; i < job.length; i++)
            { //while in length of the created job list
			    String idNumber = String.valueOf(job[i].getjobID()); //the id of the job is written
			    String remaining = String.valueOf(job[i].getRemaining()); //the remaining time is written
			    String segment = String.valueOf(job[i].getmemSeg()); //the segment of memory of the job is written
				
			    if(idNumber.length() == 1) 
				    idNumber += " ";
			    if(remaining.length() == 1) 
				    remaining += " ";
			    if(segment.length() == 1) 
				    segment += " ";	
				try
                { 
					w.write(timeSeg + "\t\t"); //these will format the information to match the info bar.
					w.write(idNumber + "\t\t");
					w.write(segment + "\t\t\t");
					w.write(job[i].getSize() + "\t\t\t\t");
					w.write(remaining + "\t\t\t");
					w.write(job[i].getStatus() + "\n");
				}
				catch(Exception e){System.out.println(e.toString());} 
		    }
	    }
    }	
}