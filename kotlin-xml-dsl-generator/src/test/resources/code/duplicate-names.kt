@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `DuplicateNameContainer`(nodeName: String) : Node(nodeName) {
	inner class `DuplicateName` : org.redundent.generated.`DuplicateName`("DuplicateName")
}

fun `DuplicateNameContainer`.`DuplicateName`(__block__: `DuplicateNameContainer`.`DuplicateName`.() -> Unit) {
	val `DuplicateName` = `DuplicateName`()
	`DuplicateName`.apply(__block__)
	this.addElement(`DuplicateName`)
}

open class `DuplicateNames`(nodeName: String) : org.redundent.generated.`DuplicateNameContainer`(nodeName)

fun `DuplicateNames`(__block__: `DuplicateNames`.() -> Unit): `DuplicateNames` {
	val `DuplicateNames` = `DuplicateNames`("DuplicateNames")
	`DuplicateNames`.apply(__block__)
	return `DuplicateNames`
}

open class `DuplicateName`(nodeName: String) : Node(nodeName)