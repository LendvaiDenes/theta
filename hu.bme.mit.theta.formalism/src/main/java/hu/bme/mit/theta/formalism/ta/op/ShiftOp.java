package hu.bme.mit.theta.formalism.ta.op;

import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.RatType;
import hu.bme.mit.theta.formalism.common.decl.ClockDecl;

public interface ShiftOp extends ClockOp {

	public ClockDecl getClock();

	public int getOffset();

	@Override
	public AssignStmt<RatType, RatType> toStmt();

}
