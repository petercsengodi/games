package hu.csega.editors.anm.treeview;

import java.util.List;

public interface AnimatorTreeNode {

	List<? extends AnimatorTreeNode> getChildren();

}
