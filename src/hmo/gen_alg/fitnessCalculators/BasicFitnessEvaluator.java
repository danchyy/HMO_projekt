package hmo.gen_alg.fitnessCalculators;

import hmo.gen_alg.Task;

import java.util.*;

public class BasicFitnessEvaluator implements FitnessCalculator {

//            int MACHINE_INDEX = 0;
//            int START_TIME_INDEX = 1;
//            int LENGTH_INDEX = 2;
//            int RESOURCE_INDEX = 3;

    public boolean arrayContainsItem(int[] array, int item) {
        for (int i=0; i < array.length; i++) {
            if (array[i] == item) {
                return true;
            }
        }
        return false;
    }

    public int findLatestResource(Map<Integer, List<Integer>> machineTaskStartMap,
                                  Map<Integer, Integer> resourceMap, int[] requiredResources) {

        int latestEnd = 0;
        for (Map.Entry<Integer, Integer> entry : resourceMap.entrySet()) {
            Integer resource = entry.getKey();
            if (!arrayContainsItem(requiredResources, resource)){
                continue;
            }
            Integer taskIndex = entry.getValue();
            List<Integer> informationList = machineTaskStartMap.get(taskIndex);
            int taskEnd = informationList.get(END_INDEX);
            if (latestEnd == -1 || taskEnd > latestEnd) {
                latestEnd = taskEnd;
            }
        }
        return latestEnd;
    }

    @Override
    public double calculateFitness(int[] taskArray, int[] permutationArray, List<Task> tasks) {
        // To map tasks with resources and machines and length
        Map<Integer, List<Integer>> machineTaskStartMap = new HashMap<>();

        // Map all active resources
        Set<Integer> activeResources = new HashSet<>();

        Map<Integer, Integer> machineTimer = new HashMap<>();

        // Mapping of resources to tasks
        Map<Integer, Integer> resourceMap = new HashMap<>();

        int maxCount = 0; // COUNTER OF TOTAL TIMER, COUNTS ONLY MAX TIME ON MACHINE

        for (int i=0; i < permutationArray.length; i++) {
            // Getting the index of a task which is performed i-th.
            int taskIndex = permutationArray[i];
            Task task = tasks.get(taskIndex);

            int taskLength = task.getLength();


            // Determining machine
            int machineToUse = taskArray[taskIndex];
            int currentLengthOnMachine = -1;
            // Determining the start time of the task
            if (machineTimer.containsKey(machineToUse)) {
                currentLengthOnMachine = machineTimer.get(machineToUse);
            } else {
                currentLengthOnMachine = 0;
            }

            // Determining the start and end time of the task
            int startOfTask = -1;
            int[] requiredResources = task.getRequiredResources();
            if (requiredResources.length != 0) {
                int resourceConditionEndTime = findLatestResource(machineTaskStartMap, resourceMap, requiredResources);
                startOfTask = Math.max(currentLengthOnMachine, resourceConditionEndTime);
            } else {
                startOfTask = currentLengthOnMachine;
            }
            int endOfTask = startOfTask + taskLength;
            machineTimer.put(machineToUse, endOfTask);

            List<Integer> informationList = new ArrayList<>();

            if (endOfTask > maxCount) {
                maxCount = endOfTask;
            }

            informationList.add(machineToUse);
            informationList.add(currentLengthOnMachine);
            informationList.add(endOfTask);

            machineTaskStartMap.put(taskIndex, informationList);
            for (int j=0; j < requiredResources.length; j++) {
                resourceMap.put(requiredResources[j], taskIndex);
            }

        }
        return maxCount;
    }

    @Override
    public List<String> evaluate(int[] taskArray, int[] permutationArray, List<Task> tasks) {
        // To map tasks with resources and machines and length
        Map<Integer, List<Integer>> machineTaskStartMap = new HashMap<>();

        // Map all active resources
        Set<Integer> activeResources = new HashSet<>();

        Map<Integer, Integer> machineTimer = new HashMap<>();

        // Mapping of resources to tasks
        Map<Integer, Integer> resourceMap = new HashMap<>();

        List<String> runtimeInfo = new ArrayList<>();

        int maxCount = 0; // COUNTER OF TOTAL TIMER, COUNTS ONLY MAX TIME ON MACHINE

        for (int i=0; i < permutationArray.length; i++) {
            // Getting the index of a task which is performed i-th.
            int taskIndex = permutationArray[i];
            Task task = tasks.get(taskIndex);

            int taskLength = task.getLength();


            // Determining machine
            int machineToUse = taskArray[taskIndex];
            int currentLengthOnMachine = -1;
            // Determining the start time of the task
            if (machineTimer.containsKey(machineToUse)) {
                currentLengthOnMachine = machineTimer.get(machineToUse);
            } else {
                currentLengthOnMachine = 0;
            }

            // Determining the start and end time of the task
            int startOfTask = -1;
            int[] requiredResources = task.getRequiredResources();
            if (requiredResources.length != 0) {
                int resourceConditionEndTime = findLatestResource(machineTaskStartMap, resourceMap, requiredResources);
                startOfTask = Math.max(currentLengthOnMachine, resourceConditionEndTime);
            } else {
                startOfTask = currentLengthOnMachine;
            }
            int endOfTask = startOfTask + taskLength;
            machineTimer.put(machineToUse, endOfTask);

            List<Integer> informationList = new ArrayList<>();

            if (endOfTask > maxCount) {
                maxCount = endOfTask;
            }

            informationList.add(machineToUse);
            informationList.add(currentLengthOnMachine);
            informationList.add(endOfTask);

            machineTaskStartMap.put(taskIndex, informationList);
            for (int j=0; j < requiredResources.length; j++) {
                resourceMap.put(requiredResources[j], taskIndex);
            }

            if (requiredResources.length > 0) {
                String resourceString = "";
                for (int j=0; j < requiredResources.length; j++) {
                    resourceString = resourceString + "r" + String.valueOf(requiredResources[j]);
                }
                runtimeInfo.add("'t" + (taskIndex+1) + "'," + startOfTask + ",'m" + machineToUse + "'.");
            } else {
                runtimeInfo.add("'t" + (taskIndex+1) + "'," + startOfTask + ",'m" + machineToUse + "'.");
            }


        }
        return runtimeInfo;
    }
}
