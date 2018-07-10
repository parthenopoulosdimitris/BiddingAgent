from sklearn.neighbors.kde import KernelDensity
import numpy as np

def estimate():
	kde = KernelDensity(kernel = "gaussian", bandwidth = 0.1).fit(X)
	#get random sample
	samples = kde.sample()

def main(): 
	##we want elements as a list. 
	for arg in sys.argv[1:]:
	question_two()

if __name__ == '__main__': 
	main()