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
package hu.bme.mit.theta.core.stmt;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.theta.common.Utils;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntType;

import java.util.List;
import java.util.stream.Collectors;

public final class AtMostStmt implements Stmt {

	private static final int HASH_SEED = 929;
	private static final String STMT_LABEL = "at-most";

	private final List<VarDecl<BoolType>> varDecls;
	private final Expr<IntType> sum;

	private volatile int hashCode = 0;

	private AtMostStmt(final List<VarDecl<BoolType>> varDecls,final Expr<IntType> sum) {
		this.varDecls = checkNotNull(varDecls);
		this.sum = checkNotNull(sum);
	}

	public static AtMostStmt of(final List<VarDecl<BoolType>> varDecls, final Expr<IntType> sum) {
		return new AtMostStmt(varDecls, sum);
	}

	public List<VarDecl<BoolType>> getVarDecls() {
		return varDecls;
	}

	public Expr<IntType> getSum() { return sum; }

	@Override
	public <P, R> R accept(final StmtVisitor<? super P, ? extends R> visitor, final P param) {
		return visitor.visit(this, param);
	}

	@Override
	public int hashCode() {
		int result = hashCode;
		if (result == 0) {
			result = HASH_SEED;
			result = 33 * result + varDecls.hashCode() + sum.hashCode();
			hashCode = result;
		}
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof AtMostStmt) {
			final AtMostStmt that = (AtMostStmt) obj;
			return this.getVarDecls().equals(that.getVarDecls()) && this.sum.equals(that.sum);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Utils.lispStringBuilder(STMT_LABEL).add(sum.toString()+" "+varDecls.stream().map((decl) -> decl.getName()).collect(Collectors.joining(","))).toString();
	}
}
