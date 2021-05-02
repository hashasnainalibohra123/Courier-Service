package com.everest.engineering.services.impl;

import com.everest.engineering.model.CurierJob;
import com.everest.engineering.model.DeliveryQuery;
import com.everest.engineering.model.VehicleData;
import com.everest.engineering.services.CostCalculationService;
import com.everest.engineering.services.ProcessInput;
import com.everest.engineering.services.TimeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProcessInputImpl implements ProcessInput {

    @Autowired
    CostCalculationService costCalculationService;

    VehicleData vehicleData;

    public void processInput()
    {
        Scanner in = new Scanner(System.in);
        while(true)
        {
            // Using Scanner for Getting Input from User

            //get the base_delivery_cost no_of_packges
            String str = in.nextLine();
            String baseCost = str.split(" ")[0];
            String noOfPkg = str.split(" ")[1];
            CurierJob curierJob = CurierJob.builder().build();
            curierJob.setBaseDeliveryCost(Integer.parseInt(baseCost));
            curierJob.setNoOfPackages(Integer.parseInt(noOfPkg));
            List jobQueries = new ArrayList<DeliveryQuery>();

            // the items in the treeset will automatically will sorted by the weight;

            for(int index = 0; index < Integer.parseInt(noOfPkg); index++)
            {
                //pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
                String[] queryStrings= in.nextLine().split(" ");
                DeliveryQuery query = DeliveryQuery.builder().build();
                query.setPackageId(queryStrings[0]);
                query.setPkgWeigth(Integer.parseInt(queryStrings[1]));
                query.setPkgDistance(Integer.parseInt(queryStrings[2]));
                query.setOfferCode(queryStrings[3]);
                jobQueries.add(query);
            }
            curierJob.setDeliveryQuery(jobQueries);

            String vehicalDataStr = in.nextLine();
            //This is the singleton bean
            vehicleData = new VehicleData();
            vehicleData.setCount(Integer.parseInt(vehicalDataStr.split(" ")[0]));
            vehicleData.setMaxSpeed(Integer.parseInt(vehicalDataStr.split(" ")[1]));
            vehicleData.setMaxLoad(Integer.parseInt(vehicalDataStr.split(" ")[2]));
            curierJob.setVehicleData(vehicleData);
            //callign the service for
            costCalculationService.calculateCost(curierJob);

        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3, 4, 5};
        List<int[]> allPossibleCombinatiosForNumbers = new ArrayList<>();
        for (int i = 2; i < numbers.length; i++) {
            allPossibleCombinatiosForNumbers.addAll(getCombinationsOfNElements(numbers, i));
        }
        for (int[] combination : allPossibleCombinatiosForNumbers) {
            printArray(combination);
            printArrayIfNumbersSumExpectedValue(combination, 6);
        }
    }


    private static List<int[]> getCombinationsOfNElements(int[] numbers, int n) {
        int elementsKnown = n - 1;
        List<int[]> allCombinationsOfNElements = new ArrayList<>();
        for (int i = 0; i < numbers.length - elementsKnown; i++) {
            int[] combination = new int[n];
            for (int j = 0; j < n; j++) {
                combination[j] = numbers[i + j];
            }
            allCombinationsOfNElements.addAll(generationCombinations(combination, i + elementsKnown, numbers));
        }
        return allCombinationsOfNElements;
    }

    private static List<int[]> generationCombinations(int[] knownElements, int index, int[] numbers) {
        List<int[]> combinations = new ArrayList<>();
        for (int i = index; i < numbers.length; i++) {
            int[] combinationComplete = Arrays.copyOf(knownElements, knownElements.length);
            combinationComplete[knownElements.length - 1] = numbers[i];
            combinations.add(combinationComplete);
        }
        return combinations;
    }

    private static void printArray(int[] numbers) {
        System.out.println();
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }

    private static void printArrayIfNumbersSumExpectedValue(int[] numbers, int expectedValue) {
        int total = 0;
        for (int i = 0; i < numbers.length; i++) {
            total += numbers[i];
        }
        if (total == expectedValue) {
            System.out.print("\n ----- Here is a combination you are looking for -----");
            printArray(numbers);
            System.out.print("\n -------------------------------");
        }
    }
}
