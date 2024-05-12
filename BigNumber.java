
/**
 * Write a description of class BigNumber here.
 *
 * @author (Hadassa Kazhav)
 * @version (14/1/21)
 */
public class BigNumber
{

    // instance variable
    // represents the head of linked list 
    private IntNode _head; 

    /**
     * Constructor 1
     * Constructor empty that initializes the list to with number 0
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public BigNumber()
    {
        _head = new IntNode(0);
    }

    /**
     * 
     * Constructor 2
     * Constructor builder that receives a long type number as a parameter and save it in a list format.
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public BigNumber(long num)
    {
        // get the least significant digit and make it head of list
        int leastSign = (int) (num % 10);
        // initialize head
        _head = new IntNode(leastSign);

        num = num / 10;
        IntNode itr = _head;
        // create a loop and add all digits of given number
        while(num > 0) { 
            leastSign = (int) (num % 10);
            num = num / 10;
            // add digit to the list
            itr.setNext(new IntNode(leastSign));
            // move iterator to next node
            itr = itr.getNext();
        }
    }

    /**
     * Constructor 3
     * Constructor - Copy builder other.
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public BigNumber(BigNumber other)
    {
        // create a copy of head node
        this._head = new IntNode(other._head.getValue());

        IntNode itr1 = this._head;
        IntNode itr2 = other._head.getNext();
        // create a loop to copy other nodes
        while(itr2 != null) {
            itr1.setNext(new IntNode(itr2.getValue()));

            itr1 = itr1.getNext();
            itr2 = itr2.getNext();
        }
    }

    //   Methods:
    /**
     * This method a string 
     * @returns string the number represented in the list.
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public String toString(){
        // use helper method to recursive
        return toString(_head);
    }

    /**
     * This method private recursive helper method
     * @returns recursive step convert first digit to string and add it as least significant digit 
     * then solve remaining number recursively
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    private String toString(IntNode number) {
        if(number == null) {
            return "";
        }
        // convert first digit to string and add it as least significant digit
        return toString(number.getNext()) + Integer.valueOf(number.getValue());
    }

    /**
     * This method compares two large numbers
     * @ param BigNumber other
     * @returns the value -1, if the number on which the method is applied is smaller than the number obtained as a parameter
     * @returns the value 1,if the number on which the method is applied is greater than the number obtained as a parameter
     * @returns 0, if the numbers are equal
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int compareTo (BigNumber other){
        // Create a variable if the results are equal
        int result = 0;

        // create a loop to compare each nodes
        IntNode itr1 = _head;
        IntNode itr2 = other._head;
        while(!(itr1 == null || itr2 == null)) { 
            // compare current number value from nodes and update result
            if(itr1.getValue() > itr2.getValue()) {
                result = 1;
            }
            else if(itr1.getValue() < itr2.getValue()) {
                result = -1;
            }
            // if both values are equal move to next node
            itr1 = itr1.getNext();
            itr2 = itr2.getNext();
        }
        // return final result of comparison
        return result;
    }

    /**
     * This method adds to big numbers
     * @param BigNumber other
     * @returns a new BigNumber object
     * Time complexity: O(n+1) = O(n)
     * Space complexity: O(1)
     */
    public BigNumber addBigNumber (BigNumber other){
        // create a new BigNumber to return
        BigNumber result = new BigNumber();

        // create a variable to hold result for current numbers
        int number = this._head.getValue() + other._head.getValue();

        // create a variable to hold for carry 
        int carry = number / 10;
        number = number % 10;
        // set result value for first number
        result._head.setValue(number);

        // create iterators to iterate over list
        IntNode itr = result._head;
        IntNode itr1 = this._head.getNext();
        IntNode itr2 = other._head.getNext();

        // create loop to add individual numbers
        while(true) {
            // if we already computed most significant numbers for both list 
            if((itr1 == null) && (itr2 == null)) {
                // if there is a carry over left add it to next node in result
                if(carry > 0) {
                    itr.setNext(new IntNode(carry));
                }
                // finish the sum
                break;
            }
            // if we done adding the first number add carry to second number
            else if(itr1 == null) {
                // compute the next number
                number = itr2.getValue() + carry;
                carry = number / 10;
                number = number % 10;

                itr.setNext(new IntNode(number));
                // move iterators
                itr = itr.getNext();
                itr2 = itr2.getNext();
            }
            // if we done adding the second number add carry to first number
            else if(itr2 == null) {
                // compute the next number
                number = itr1.getValue() + carry;
                carry = number / 10;
                number = number % 10;

                itr.setNext(new IntNode(number));
                // move iterators
                itr = itr.getNext();
                itr1 = itr1.getNext();
            }
            else {
                // both number have numbers to be added
                // compute the next number
                number = itr1.getValue() + itr2.getValue() + carry;
                carry = number / 10;
                number = number % 10;

                itr.setNext(new IntNode(number));
                // move iterators
                itr = itr.getNext();
                itr1 = itr1.getNext();
                itr2 = itr2.getNext();
            }
        }
        return result;
    }

    /**
     * This method add a number of type long to the BigNumber
     * @param number of type long
     * @returns  a new BigNumber object
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public BigNumber addLong (long num){
        // convert long to BigNumber
        BigNumber number = new BigNumber(num);
        // add two BigNumbers
        return addBigNumber(number);
    }

    /**
     * This method subtract smaller number from larger number
     * @param BigNumber other
     * @returns result new BigNumber object
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public BigNumber subtractBigNumber (BigNumber other){
        // first get the large and small numbers to perform subtraction
        BigNumber large = this;
        BigNumber small = other;
        if(this.compareTo(other) < 0) {
            large = other;
            small = this;
        }

        BigNumber result = new BigNumber();
        int number = large._head.getValue() - small._head.getValue();
        int carry = 0;
        if(number < 0) {
            number = number + 10;
            carry = -1;
        }

        result._head.setValue(number);
        // create iterators to iterate over list
        IntNode itr = result._head;
        IntNode itr1 = large._head.getNext();
        IntNode itr2 = small._head.getNext();

        // create a variable to store most significant number in result
        IntNode mostSignf = itr;
        // create loop to compute individual numbers
        while(true) {
            // if we already computed most significant numbers from large list 
            //then stop the loop and return the result
            if(itr1 == null) {
                break;
            }
            // if we done removing the small number add carry to large number
            else if(itr2 == null) {
                number = itr1.getValue() + carry;
                carry = 0;
                if(number < 0) {
                    number = number + 10;
                    carry = -1;
                }

                itr.setNext(new IntNode(number));
                // move iterators
                itr = itr.getNext();
                itr1 = itr1.getNext();

                // update most significant number
                if(number > 0){
                    mostSignf = itr;
                }

            }
            else {
                // both number have numbers to be computed
                // compute the next number
                number = itr1.getValue() - itr2.getValue() + carry;
                // compute new carry and resulting number
                carry = 0;
                if(number < 0) {
                    number = number + 10;
                    carry = -1;
                }
                // add next number in result
                itr.setNext(new IntNode(number));
                // move iterators
                itr = itr.getNext();
                itr1 = itr1.getNext();
                itr2 = itr2.getNext();
                
                // update most significant number
                if(number > 0){
                    mostSignf = itr;
                }
            }
        }
        // remove ant preceding zeros from result by keeping most significant number as the last node
        mostSignf.setNext(null);
        return result;
    }

    /**
     * This method the multiplication of two large numbers
     * @param BigNumber other
     * @returns result new BigNumber object
     * Time complexity: O(n^2 + n)
     * Space complexity: O(n^2) (we are using n temporary results to add to the final result)
     */
    public BigNumber multBigNumber (BigNumber other){
        // create a new BigNumber to return
        BigNumber result = new BigNumber();

        // create iterators to iterate over list
        IntNode itr = other._head;
        int place = 0; 
        while(itr != null) {
            // outer loop to multiply each number of second number with the first number
            int multi = itr.getValue(); // current number to multiply with
            itr = itr.getNext();

            BigNumber temp = new BigNumber();

            // create iterator to loop over list
            IntNode itr2 = temp._head;

            // add zeros for number of places
            for(int i=0; i<place; i++) {
                itr2.setNext(new IntNode(0));
                itr2 = itr2.getNext();
            }

            // create a variable to hold result for current numbers
            int number = this._head.getValue() * multi;

            int carry = number / 10;
            number = number % 10;
            itr2.setValue(number);

            IntNode itr1 = this._head.getNext();
            while(itr1 != null) {
                // get carry and number value for current number multiplication
                number = (itr1.getValue() * multi) + carry;
                carry = number / 10;
                number = number % 10;

                itr2.setNext(new IntNode(number));

                itr2 = itr2.getNext();
                itr1 = itr1.getNext();
            }
            // add temporary result to final result
            result = result.addBigNumber(temp);
            place++;          
        }
        return result;  
    }

}
