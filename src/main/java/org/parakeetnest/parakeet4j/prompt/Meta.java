package org.parakeetnest.parakeet4j.prompt;


/*
#### Meta prompts
Meta-prompts are special instructions embedded within a prompt to guide a language model in generating a specific kind of response.

|  Meta-Prompt   |  Purpose  |
| :------------  | :-------- |
|[Brief] What is Kubernetes? | For a concise answer
|[In Layman’s Terms] Explain machine learning | For a simplified explanation
|[As a Story] Describe the evolution of programming languages | To get the information in story form
|[Pros and Cons] Should our company move to the cloud? | For a balanced view with advantages and disadvantages
|[Step-by-Step] How do I set up a VPN? | For a detailed, step-by-step guide
|[Factual] What is the current version of Python? | For a straightforward, factual answer
|[Opinion] Which is better for backend development: Node.js or Django? | To get an opinion-based answer
|[Comparison] Compare SQL databases and NoSQL databases| For a comparative analysis
|[Timeline] What are the key milestones in cybersecurity? | For a chronological account of key events
|[As a Poem] Tell me about coding | For a poetic description
|[For Kids] How does the internet work? | For a child-friendly explanation
|[Advantages Only] What are the benefits of using containers? | To get a list of only the advantages
|[As a Recipe] How to write a Python script for automating tasks? | To receive the information in the form of a recipe
*/

public class Meta {
    public static String Brief(String s) {
        return "[Brief] " + s;
    }

    public static String InLaymansTerms(String s) {
        return "[In Layman's Terms] " + s;
    }

    public static String AsAStory(String s) {
        return "[As a Story] " + s;
    }

    public static String ProsAndCons(String s) {
        return "[Pros and Cons] " + s;
    }

    public static String StepByStep(String s) {
        return "[Step-by-Step] " + s;
    }

    public static String Factual(String s) {
        return "[Factual] " + s;
    }

    public static String Opinion(String s) {
        return "[Opinion] " + s;
    }

    public static String Comparison(String s) {
        return "[Comparison] " + s;
    }

    public static String Timeline(String s) {
        return "[Timeline] " + s;
    }

    public static String AsAPoem(String s) {
        return "[As a Poem] " + s;
    }

    public static String ForKids(String s) {
        return "[For Kids] " + s;
    }

    public static String AdvantagesOnly(String s) {
        return "[Advantages Only] " + s;
    }

    public static String AsARecipe(String s) {
        return "[As a Recipe] " + s;
    }
}