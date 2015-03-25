/*
 * @Jared Starr   
 * @assignment CS350 Term Project 
 * @date 04/08/2014
 *
*/

import java.io.*;

public class Main
{
	public static void main(String[] args) 
	{
		int totalJobs = 20; //this is where we set how many jobs we want to create
		Job[] job = new Job[totalJobs]; //this array will hold the original vaules of each of the jobs
		Job[] jOne = new Job[totalJobs]; //this is job one, for use with caseOne
		Job[] jTwo = new Job[totalJobs]; //this is job two, for use with caseTwo
		Job[] jThree = new Job[totalJobs]; //this is job three, for use with caseThree
			
		//The creation of the random jobs for the job array, as well as the duplication of this original array into the other three job arrays
		for(int i = 0; i < totalJobs; i++)
		{ //this loop will create the random jobs for the job array, then copy them into the job specific arrays
			job[i] = new Job();
			job[i].setJobID(i + 1); //here we are create the original Job array and putting data into it.
                        
			jOne[i] = new Job(); //this will copy that data into jOne
			jTwo[i] = new Job(); //this will copy it into jTwo
			jThree[i] = new Job(); //this will copy it into jThree
                        
			jOne[i].setSize(job[i].getSize()); //for each of these, the first like set's the job size in that array using the same space value from the original job array.
			jOne[i].setJobID(job[i].getjobID()); //this one sets the ID the same way
			jOne[i].setexTime(job[i].getexTime()); //then this sets the execution time
			jOne[i].setRemaining(); //and this sets the remaining time
                        
			jTwo[i].setSize(job[i].getSize());
			jTwo[i].setJobID(job[i].getjobID());
			jTwo[i].setexTime(job[i].getexTime());
			jTwo[i].setRemaining();
                        
			jThree[i].setSize(job[i].getSize());
			jThree[i].setJobID(job[i].getjobID());
			jThree[i].setexTime(job[i].getexTime());
			jThree[i].setRemaining();
		}
		Segment[] caseOne = new Segment[7]; //these three lines all create the memory arrays for the three cases.
		Segment[] caseTwo = new Segment[7];
		Segment[] caseThree = new Segment[7];
		
		for(int i = 0; i < caseOne.length; i++) //just picked one of the array lengths since they're all increasing at the same rate.
		{
			caseOne[i] = new Segment();
            caseTwo[i] = new Segment();
            caseThree[i] = new Segment();
		}
		
		//the following will set all of the memory segments data based on what was given in the instructions for case one
		caseOne[0].setSize(32); //32 mb
		caseOne[1].setSize(48); //48 mb
		caseOne[2].setSize(24); //24 mb
		caseOne[3].setSize(16); //16 mb
		caseOne[4].setSize(64); //64 mb
		caseOne[5].setSize(40); //40 mb
		caseOne[6].setSize(32); //32 mb
        //memory segments for case 2
		caseTwo[0].setSize(32);
		caseTwo[1].setSize(48);
		caseTwo[2].setSize(24);
		caseTwo[3].setSize(16);
		caseTwo[4].setSize(64);
		caseTwo[5].setSize(40);
		caseTwo[6].setSize(32);
        //memory segments for case 3
		caseThree[0].setSize(32);
		caseThree[1].setSize(48);
		caseThree[2].setSize(24);
		caseThree[3].setSize(16);
		caseThree[4].setSize(64);
		caseThree[5].setSize(40);
		caseThree[6].setSize(32);
		
		caseOne Acase = new caseOne(jOne, caseOne); //this will create the actual cases by calling in the classes designed for each of them
		caseTwo Bcase = new caseTwo(jTwo, caseTwo);
		caseThree Ccase = new caseThree(jThree, caseThree);
	}
}