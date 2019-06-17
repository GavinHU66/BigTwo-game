
#!/bin/bash

for i in {0..12}
do
    for j in {0..3}
    do
        mv $i$j.gif $i$j.jpg
    done
done

