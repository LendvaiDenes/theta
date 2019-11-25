package hu.bme.mit.theta.xcfa.simulator;

import hu.bme.mit.theta.cfa.CFA;
import hu.bme.mit.theta.core.decl.Decl;
import hu.bme.mit.theta.core.decl.IndexedConstDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.utils.VarIndexing;
import hu.bme.mit.theta.xcfa.XCFA;
import hu.bme.mit.theta.xcfa.XCFA.Process.Procedure.Location;

import java.util.Optional;

/**
 * Stores static procedure data needed by CallState
 * Wrapper for XCFA.Procedure
 * TODO use this to cache data calculated every time in CallState
 */
public class ProcedureData {
	private XCFA.Process.Procedure procedure;

	public ProcedureData(XCFA.Process.Procedure procedure) {
		this.procedure = procedure;
	}

	/**
	 * VarIndexing is used for simulating the stack. This is used when this procedure is called.
	 * @param state The RuntimeState to be modified
	 */
	public void pushProcedure(RuntimeState state) {
		// result is a variable, it is already pushed here
		for (VarDecl<?> var: procedure.getVars()) {
			state.vars = state.vars.inc(var);
		}
		for (VarDecl<?> var: procedure.getParams()) {
			state.vars = state.vars.inc(var);
		}
	}

	private IndexedConstDecl<? extends Type> getCurrentVar(VarDecl<?> var, RuntimeState state) {
		return var.getConstDecl(state.vars.get(var));
	}

	public void popProcedure(RuntimeState state) {
		for (VarDecl<?> var: procedure.getVars()) {
			state.valuation.remove(getCurrentVar(var, state));
			state.vars = state.vars.inc(var, -1);
		}
		for (VarDecl<?> var: procedure.getParams()) {
			state.valuation.remove(getCurrentVar(var, state));
			state.vars = state.vars.inc(var, -1);
		}
	}

	public Location getInitLoc() {
		return procedure.getInitLoc();
	}

	public Location getErrorLoc() {
		return procedure.getErrorLoc();
	}

	public int getParamSize() {
		return procedure.getParams().size();
	}

	public VarDecl<? extends Type> getParam(int i) {
		return procedure.getParams().get(i);
	}

	public Optional<Decl<? extends Type>> getCurrentResultVar(RuntimeState state) {
		if (procedure.getResult() == null)
			return Optional.empty();
		return Optional.of(getCurrentVar(procedure.getResult(), state));
	}

	public Location getFinalLoc() {
		return procedure.getFinalLoc();
	}
}
