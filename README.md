# MakeupApp

**Model**: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.
**View**: The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.
**ViewModel**: It exposes those data streams which are relevant to the View. Moreover, it servers as a link between the Model and the View.
