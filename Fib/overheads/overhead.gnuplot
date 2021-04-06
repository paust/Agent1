reset
set terminal png size 1000,1000
set title "Run Time of fib(n) With Different Agents"
set xlabel "n"
set ylabel "Run Time"
set border 3
plot for[i=2:5:1] 'overhead.data' using 1:(column(i) / 1000) with lines title col
quit
