package fr.yorkgangsta.metiers.jobs;

public class PlayerSave {
  private final Job job;

  public PlayerSave(Job job){
    this.job = job;
  }

  public Job getJob() { return job; }
}
