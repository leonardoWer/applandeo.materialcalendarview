# Material-calendar-view
## How I used applandeo
> In this repository, I will leave an example of using the applandeo calendar code and explain the problem I encountered when using it

## Template
My code in [this file](calendar_with_icons_on_calendar_days)

## My problem
I used ChatGPT4, which was able to help me with the basic code, 
but I ran into a problem with the icons.   
I tried everything I could, but it just wouldn't work.  
[The documentation was clear and understandable](https://github.com/Applandeo/Material-Calendar-View), as I did. 
As a result, the problem turned out to be that I used incorrect date parsing. 
**I used a non-standard locale in my calendar, which ruined my icon installation**, so when working with applandeo, *pay attention to this!*

## Result
![Снимок экрана 2024-12-22 120046](https://github.com/user-attachments/assets/9e4c286e-9848-4638-9485-4c1e8d83b94a)

#### Thanks applandeo for this a simple and convenient calendar and competent documentation
