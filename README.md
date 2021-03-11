# FitNet

Fitnet is an app project that a colleague and I created during our apprenticeship. The goal was to store our fitness plans on our smartphones rather than paper. 

## Motivation
While working out in the gym, we noticed that it is quite difficult to remember the weight and the number of repetitions for each exercise. That's why we decided to write an app that would do this for us. The implementation finally took place in the course of the training company lessons of the EDP schools of the district of Deggendorf.

## Goals

The goal of the project is to create an Android app that can be used to create training plans with associated exercises. After successful creation and saving, it should be possible to train with a training plan.

## Time-period/Participants

The project was implemented in the period from February to July 2017, the team consisted of Benedikt Halbritter and Markus Schottenhammer.

## Software used

The whole project was implemented in Android Studio 2.3, and we used Java as the programming language. For backup, we used GitHub, as this repository works best with Android Studio. Other repositories like Subversion or GitLab caused significant problems and are not recommended from our point of view.

## Faced Problems

### Repository

For the training company projects there is the requirement to store the program code in a repository, since this was our first project with Android Studio this led to very big difficulties that cost us a lot of time. In the following subsections we will explain why we finally used GitHub. 

### Subversion 

Since Subversion is used at the computer schools in almost all subjects for the backup of the individual projects, this was the first repository we wanted to use.
With Subversion we had the problem that AndroidStudio is not designed for it and therefore the commit and checkout led to such problems that we decided to use another repository. The decision fell on GitLab, because it is also installed on the school servers.

### Github 

GitLab simply overwrites the old state with the new one without comparing both, so we created 2 branches to store our individual states and merge both to the master branch, but the merge request did not work reliably in places and threw us files in disarray, which completely erased our progress so far, eventually we had to delete the project and start over. Another disadvantage is that it is not as easy to continue working on the project from home as on GitHub, since we used the GitLab on the school servers. 

### different Android Studio Versions

Android version 2.1.2 is installed on the school computers (as of 30.03.2017). The current version is already 2.3, online the version 2.1.2 was not, or only from dubious sources to find, for this reason, the Android Studio versions we used at home and at school were different. 
These different versions lead to serious problems in different places, so we decided to use our own laptops instead of the school computers to avoid this problem.

### Conclusion

Due to the big problems we had with the different repositories, we unfortunately lost a lot of time towards the end of the project. Because of this lost time and the lack of experience with Android development, we were only able to implement a very small part of our original ideas. 	 Regarding Android programming I learned a lot in the last months and also the used JSON objects and arrays I understand much better than before.

In a nutshell:
All in all I can say about the project that it was an interesting experience to familiarize myself with Android development, which was completely unknown until the project. I also enjoyed the project despite the problems.
