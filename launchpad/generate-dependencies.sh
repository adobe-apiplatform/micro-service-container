#!/bin/bash

# Tree graphics
readonly prefix_1='+- '
readonly prefix_2='|  '
readonly prefix_3='\\- ' # Backslash must be escaped
readonly adobe_group_id='com.adobe'

# Keep just the actual dependency tree, removing everything else
mvn dependency:tree | grep -e ${prefix_1} -e ${prefix_2} -e ${prefix_3} > dependency-tree.txt
# Remove [INFO] prefix, tree graphics and whitespace
# Remove lines containing Adobe-specific dependencies
# Sort the output
sed -e "s/\[INFO]//g; s/${prefix_1}//g; s/${prefix_2}//g; s/${prefix_3}//g; s/ //g" dependency-tree.txt | grep -v ${adobe_group_id} | sort > dependency-list.txt
