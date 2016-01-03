
// `timescale 1 ns / 1 ns

`define false 0
`define true 1

module shutter(clk, ce, din, latch, dout);
   
   parameter din_width = 6;
   parameter dout_width = 6;

   input     clk, ce;
   input [din_width-1:0] din;
   input latch;
   output [dout_width-1:0] dout;
   wire [dout_width-1:0]  dout;
   reg [dout_width-1:0]   dout_reg;
   reg 			  dout_valid_reg;

   reg [din_width-1:0] 	 dly_din;

   assign 		 dout = dout_reg;
   
   // Delay input on clock cycle
   // always @(posedge clk or ce) begin : sample
   always @(posedge clk) begin : sample
      if(ce & latch) begin
	 dly_din <= din;
      end
   end

   // Mux that selects current value or previous value.
   always @(ce or latch or din or dly_din) begin : mux
      if(!ce | !(latch)) begin
	 dout_reg <= dly_din;
      end else begin
	 dout_reg <= din;
      end
   end
endmodule // shutter

