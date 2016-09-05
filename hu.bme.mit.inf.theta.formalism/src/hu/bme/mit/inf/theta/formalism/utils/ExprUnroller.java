package hu.bme.mit.inf.theta.formalism.utils;

import java.util.Collection;

import hu.bme.mit.inf.theta.core.expr.Expr;
import hu.bme.mit.inf.theta.core.type.BoolType;

public interface ExprUnroller {
	Expr<? extends BoolType> unroll(final Expr<? extends BoolType> expr, final int i);

	Collection<? extends Expr<? extends BoolType>> unroll(final Collection<? extends Expr<? extends BoolType>> exprs, final int i);
}