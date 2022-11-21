import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
public class Randomiser {
    public static ArrayList<Family> participatingFamilies = new ArrayList<Family>();
    public void newRandomiser(){
        try (Scanner scanner = new Scanner(System.in)) {
            Randomiser randomiser = new Randomiser();
            randomiser.addFamilies(scanner);
            randomiser.addPersons(scanner);
            System.out.println("Now finding assignments....");
            randomiser.secretSantaRandomiser(scanner);
            System.out.println("FINISHED");
            return;
        } catch( Exception e){
            System.out.println("ERROR: Scanner set-up");
            System.out.println(e);
            return;
        }
    }
    public void addFamilies(Scanner scanner){
            System.out.println("What families will be participating in this game?");
            System.out.println("(Last name only please)");
            System.out.println("Please input 'exit' when you're finished");
            String userInput = "";
            Boolean finishedFamilyInputs = false;
            while(finishedFamilyInputs == false){
                userInput = scanner.nextLine();
                if(userInput.equals("EXIT") || userInput.equals("exit") || userInput.equals("Exit")){
                    System.out.println("Exiting...");
                    finishedFamilyInputs = true;
                    break;
                }
                Family newFamily = new Family();
                newFamily.lastName = userInput;
                newFamily.participating = true;
                participatingFamilies.add(newFamily);
                //scanner.nextLine();
            }
            System.out.println("The families participating will be:");
            for(int i=0; i < participatingFamilies.size(); i++){
                System.out.println("* " + participatingFamilies.get(i).lastName);
            }
            System.out.println("-----");
        return;
    }
    public void addPersons(Scanner scanner){
            //Asking for each member in the family
            int numPersons = 0;
            for(int i=0; i < participatingFamilies.size();i++){
                Family currFamily = participatingFamilies.get(i);
                System.out.println("Who are the members in the " + currFamily.lastName + " Family?");
                System.out.println("(First names only please and in a CSV list)");
                //System.out.println("Please input 'exit' when you're finished");
                String personInput = scanner.nextLine();
                /*while(finishedPersonInputs == false){
                    personInput = scanner.nextLine();
                    if(personInput.equals("EXIT") || personInput.equals("exit") || personInput.equals("Exit")){
                        System.out.println("Exiting Family...");
                        finishedPersonInputs = true;
                        break;
                    }
                    Person newPerson = new Person();
                    newPerson.firstName = personInput;
                    participatingFamilies.get(i).participatingPersons.add(newPerson);
                }*/
                String[] elements = personInput.split(",");
                List<String> fixedLenghtList = Arrays.asList(elements);
                ArrayList<String> listOfFamilyMemebers = new ArrayList<String>(fixedLenghtList);
                int difference = listOfFamilyMemebers.size() - numPersons;
                //numPersons += listOfFamilyMemebers.size();
                System.out.println("listOfFamilyMemebers.size(): " + listOfFamilyMemebers.size());
                System.out.println("numPersons: " + numPersons);
                System.out.println("difference: " + difference);
                for(int k=numPersons; k<listOfFamilyMemebers.size();k++){
                    Person newPerson = new Person();
                    newPerson.firstName = listOfFamilyMemebers.get(k);
                    newPerson.lastName = currFamily.lastName;
                    currFamily.participatingPersons.add(newPerson);
                }
                numPersons += listOfFamilyMemebers.size() - difference;
                System.out.println("New num Persons: " + numPersons);
                System.out.println("The people participating in the " + currFamily.lastName + " Family are:");
                for(int j=0; j<currFamily.participatingPersons.size();j++){
                    System.out.println("* " + currFamily.participatingPersons.get(j).firstName);
                }
            }
            System.out.println("The persons participating this year are:");
            for(int i=0; i<participatingFamilies.size();i++){
                System.out.println("- " + participatingFamilies.get(i).lastName + ":");
                for(int j=0;j<participatingFamilies.get(i).participatingPersons.size();j++){
                    System.out.println("     * " + participatingFamilies.get(i).participatingPersons.get(j).firstName + " " + participatingFamilies.get(i).lastName);
                }
            }
            System.out.println("-----");
            return;
    }
    public void secretSantaRandomiser(Scanner scanner){
        //TODO:
        //Go through the family array
        //For every person:
        //1. add them to a allPersons ArrayList
        //After adding to list go through the allPerson's list and for every person in it:
        //1. Pick a random integer between 0 and the size of the array (minus 1 if includes whole size)
        //2. Get the person at the random number, and check to see if in same family (same last name)
        //      if same:
        //          i-- in for loop such that it goes through the same person again
        //      if different:
        //          a. check to see if the random person already has a secret Santa
        //              if does:
        //                  i. i-- for loop to go through same person again
        //              if doesn't:
        //                  i. set random person's secret santa to current person in array
        //                  ii. set current person's in array reciever as the random person
        ArrayList<Person> allParticipatingPersons = new ArrayList<Person>();
        for(int i = 0; i<participatingFamilies.size();i++){
            for(int j = 0; j<participatingFamilies.get(i).participatingPersons.size();j++){
                //System.out.println("Adding " + participatingFamilies.get(i).participatingPersons.get(j).firstName +"...");
                allParticipatingPersons.add(participatingFamilies.get(i).participatingPersons.get(j));
            }
        }
        for(int i=0; i<allParticipatingPersons.size();i++){
            Person personSetting = allParticipatingPersons.get(i);
            int rand = ThreadLocalRandom.current().nextInt(0, allParticipatingPersons.size());
            //System.out.println(personSetting.firstName);
            //System.out.println("Random Num: " + rand);
            Person randPerson = allParticipatingPersons.get(rand);
            //System.out.println("Rand Person: " + randPerson.firstName);
            if(randPerson.firstName == personSetting.firstName){
                //System.out.println("Skip 1");
                i--;
            } else{
                if(randPerson.lastName == personSetting.lastName){
                    //System.out.println("Skip 2");
                    i--;
                } else{
                    if(!randPerson.hasSecretSanta){
                        randPerson.hasSecretSanta = true;
                        randPerson.secretSantaName = personSetting;
                        personSetting.recieverName = randPerson;
                        System.out.println("\033[0;1m" + personSetting.firstName + " " + personSetting.lastName + " is " + randPerson.firstName + " " + randPerson.lastName + "'s Secret Santa");
                        System.out.println("*");
                        System.out.println("*");
                        System.out.println("*");
                    } else{
                        i--;
                    }
                }
            }
        }
    }
}
