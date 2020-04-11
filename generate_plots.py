import sys
import os
import pandas as pd

def generateGraph(df, xAxis, yAxis, filePath, title, xTitle, yTitle):
    plot = df.plot.bar(x=xAxis, y=yAxis, rot=0, figsize=(10,10))
    plot.set_ylabel(yTitle)
    plot.set_xlabel(xTitle)
    plot.set_title(title)
    plot.legend()
    plot.get_figure().savefig(filePath)

def exploredNodesGraph(df, filename):
    yAxis = ["Number of explored nodes (Only Factible)",\
            "Number of explored nodes (First Branch and Bound)",\
            "Number of explored nodes (Second Branch and Bound)"]
    xAxis = "Graph file"
    filePath = "images/" + filename + "/" + filename + "_ExploredNodes" + ".jpg"
    title = "Explored Nodes"
    xTitle = "Number of nodes"
    yTitle = "Graph file"
    generateGraph(df, xAxis, yAxis, filePath, title, xTitle, yTitle)

def solutionTimeGraph(df, filename):
    yAxis = ["Solution time (Only Factible) [in ns]",\
            "Solution time (First Branch and Bound) [in ns]",\
            "Solution time (Second Branch and Bound) [in ns]"]
    xAxis = "Graph file"
    filePath = "images/" + filename + "/" + filename + "_SolutionTime" + ".jpg"
    title = "Solution Times"
    xTitle = "Time in ns"
    yTitle = "Graph file"
    generateGraph(df, xAxis, yAxis, filePath, title, xTitle, yTitle)

def avgTimeNodeGraph(df, filename):
    yAxis = ["Average time per node (Only Factible) [in ns]",\
            "Average time per node (First Branch and Bound) [in ns]",\
            "Average time per node (Second Branch and Bound) [in ns]"]
    xAxis = "Graph file"
    filePath = "images/" + filename + "/" + filename + "_AvgTimePerNode" + ".jpg"
    title = "Average Time per Node"
    xTitle = "Time in ns"
    yTitle = "Graph file"
    generateGraph(df, xAxis, yAxis, filePath, title, xTitle, yTitle)

def solutionCostGraph(df,filename):
    yAxis = "Solution cost"
    xAxis = "Graph file"
    filePath = "images/" + filename + "/" + filename + "_SolutionCost" + ".jpg"
    title = "Solution Costs"
    xTitle = "Solution final cost"
    yTitle = "Graph file"
    generateGraph(df, xAxis, yAxis, filePath, title, xTitle, yTitle)

if __name__ == "__main__":
    df = pd.read_csv(sys.argv[1])
    filename = sys.argv[1].split("/")[1].split(".")[0]
    os.mkdir("images/"+filename)
    exploredNodesGraph(df,filename)
    solutionTimeGraph(df,filename)
    avgTimeNodeGraph(df,filename)
    solutionCostGraph(df,filename)