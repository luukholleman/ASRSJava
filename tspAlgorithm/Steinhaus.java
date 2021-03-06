/**
The MIT License (MIT)

Copyright (c) 2013 WLD01 Coaching groep

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package tspAlgorithm;

import java.util.ArrayList;

import productInfo.Location;

/**
 * Steinhaus, berekent alle permutaties
 * @author Robert John
 */
public class Steinhaus {

	private ArrayList<Location> locations = new ArrayList<Location>();
	private ArrayList<Location> shortestPath = new ArrayList<Location>();
	
	private double shortestLocationSoFar = -1;

	public Steinhaus() {
	}

	public ArrayList<Location> getShortestPathForLocation(ArrayList<Location> locations,
			Location startPoint) {
		this.locations = locations;

		int N = locations.size();
		// calculate all permutations for size
		perm(N, startPoint);

		return this.shortestPath;
	}

	public void perm(int N, Location startPoint) {
		int[] permutations = new int[N]; // permutation
		int[] inversePermutations = new int[N]; // inverse permutation
		int[] direction = new int[N]; // direction = +1 or -1
		for (int i = 0; i < N; i++) {
			direction[i] = -1;
			permutations[i] = i;
			inversePermutations[i] = i;
		}

		perm(0, permutations, inversePermutations, direction, startPoint);
	}

	public void perm(int numberOfPermutation, int[] permutations,
			int[] inversePermutations, int[] direction, Location startPoint) {

		// if a permutation is done
		if (numberOfPermutation >= permutations.length) {

			Location currentLocation = startPoint;
			double routeSize = 0;
			ArrayList<Location> shortestLocationSoFar = new ArrayList<Location>();

			// loop through all the items in the permutation
			for (int i = 0; i < permutations.length; i++) {
				routeSize += currentLocation.getDistanceTo(this.locations
						.get(permutations[i]));
				currentLocation = this.locations.get(permutations[i]);
				shortestLocationSoFar.add(currentLocation);
			}

			// if the new route size is shorter than the previous one, set the
			// new value
			if (routeSize < this.shortestLocationSoFar
					|| this.shortestLocationSoFar == -1) {
				this.shortestLocationSoFar = routeSize;
				this.shortestPath = shortestLocationSoFar;
			}

			return;
		}

		perm(numberOfPermutation + 1, permutations, inversePermutations,
				direction, startPoint);
		for (int i = 0; i <= numberOfPermutation - 1; i++) {
			int z = permutations[inversePermutations[numberOfPermutation]
					+ direction[numberOfPermutation]];
			permutations[inversePermutations[numberOfPermutation]] = z;
			permutations[inversePermutations[numberOfPermutation]
					+ direction[numberOfPermutation]] = numberOfPermutation;
			inversePermutations[z] = inversePermutations[numberOfPermutation];
			inversePermutations[numberOfPermutation] = inversePermutations[numberOfPermutation]
					+ direction[numberOfPermutation];

			perm(numberOfPermutation + 1, permutations, inversePermutations,
					direction, startPoint);
		}
		direction[numberOfPermutation] = -direction[numberOfPermutation];

	}
}