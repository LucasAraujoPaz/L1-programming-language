# compilador

Let number = 3.14.
Let boolean = True Or False.
Let string = "String".
Let array = [1, 2, 3].
Let factorial = 
	Function(Number x) -> Number:
		If x < 2 Then
    		1
  		Else
    		x * factorial(x - 1)
  		End
	End
.

Let main = Function(String x) -> Number:
	factorial(10)
End.
