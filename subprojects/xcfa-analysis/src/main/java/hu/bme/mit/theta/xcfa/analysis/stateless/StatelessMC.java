/*
 *  Copyright 2017 Budapest University of Technology and Economics
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package hu.bme.mit.theta.xcfa.analysis.stateless;

import hu.bme.mit.theta.mcm.MCM;
import hu.bme.mit.theta.xcfa.XCFA;
import hu.bme.mit.theta.xcfa.analysis.stateless.executiongraph.ExecutionGraph;

import java.io.FileWriter;
import java.io.IOException;

public final class StatelessMC {

    public static boolean check(XCFA xcfa, MCM mcm, int threads, boolean printcex, boolean allstates, boolean insitu, Integer maxdepth, boolean noPrint) {
        ExecutionGraph executionGraph = ExecutionGraph.create(xcfa, mcm, allstates, insitu, maxdepth, noPrint);
        executionGraph.execute(threads);
        if(executionGraph.getViolator().isPresent() && printcex) {
            try {
                executionGraph.getViolator().get().printGraph(new FileWriter("violator.dot"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return executionGraph.getViolator().isEmpty();
    }
}