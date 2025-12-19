This project feels quite unique compared to the past two capstones.
The others presented a challenge to be overcome, testing my knowledge of the content learned over the past few weeks. This capstone felt more like a problem-solving challenge, and tested my debugging skills. This was certainly a refreshing change of pace.

I began with the category controller, which I finished rather quickly. During this I did happen upon bug two, which I promptly fixed. The controller was just not executing the correct operation from the DAO. 
After that, naturally, I began work on the category DAO with MySql. This was more challenging, and took trial and error and quite a lot of resetting my database.

<img width="640" height="542" alt="Screenshot_20251218_092418" src="https://github.com/user-attachments/assets/3907fbc4-d950-4f63-8652-960c0516cbc3" />

This is also where I began to incorporate Insomnia testing into my debugging process. This allowed me to go back and forth, making changes, testing to see the outcome, and then figuring out where in the process things were going wrong.

Once I thought I had finished the DAO though, I faced some issues, as I still had 3 tests which were not passing. I was stuck for quite a while, but eventually found the solution. The assertions in Insomnia expect a specific response code, rather than a generic "500 Internal Server Error", which is what every method had been throwing up until then. This was mostly simple to fix, adding some annotations:

<img width="798" height="319" alt="Screenshot_20251218_103110" src="https://github.com/user-attachments/assets/63e46e2c-831c-4b69-bcf8-4a4dd953f992" />


I did have to explicitly pass an exception through a try catch though, of a different type than any of the other methods in the DAO class:

<img width="798" height="319" alt="Screenshot_20251218_103110" src="https://github.com/user-attachments/assets/9a1a5114-f82c-4acd-8f45-6305bb55069e" />

With that completed, all Insomnia tests now passed!:

<img width="429" height="976" alt="Screenshot_20251219_025220" src="https://github.com/user-attachments/assets/ec399461-2b6b-47f5-9759-5a409ac93739" />

Finally, the last remaining step was to fix bug one; filters were not returning correct results. The method for the min and max price search was almost implemented correctly, but there was a line missing in both the SQL statement, and the mapping of user input to that statement, such that the max price was ignored, and that min price was set to the max. This was a rather simple fix, but took me a minute to notice. 

Looking back on this capstone, I believe this more realistic scenario is a great test of our practical abilities, and is wholely appropriate for the end of the course, as me and my peers all anticipate the road ahead of us.
