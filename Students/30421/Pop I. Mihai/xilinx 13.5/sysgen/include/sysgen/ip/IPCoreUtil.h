/*
 *  IPCoreUtil.h
 *
 *  Copyright (c) 2007, Xilinx, Inc.  All Rights Reserved.
 *  Reproduction or reuse, in any form, without the explicit written
 *  consent of Xilinx, Inc., is strictly prohibited.
 *
 *  Description: Public, stand-alone proceedures for
 *               System Generator IPCore class and BlockDescriptor
 */
#ifndef SYSGEN_IPCOREUTIL_H
#define SYSGEN_IPCOREUTIL_H

#include "sysgen/sg_config.h"
#include "sysgen/SysgenType.h"
#include "sysgen/ip/IPCore.h"
#include "sysgen/ip/HDLBlackBox.h"
#include "anytable/AnyTable.h"
#include "sysgen/BlockDescriptor.h"

#include <string>
#include <vector>
#include <map>

namespace Sysgen {
    class BlockDescriptor;
    class PortDescriptor;
}

namespace Sysgen {
    namespace IP {
        namespace IPCoreUtil {
			SG_API
			bool preSetIPCoreFromMask(AnyTable::AnyType& blockAnyTable, IPCore& ipcore);

			SG_API
			std::string validateXCOInstrThroughBlock(XCOInstr& xcoInstr, const Sysgen::BlockDescriptor& bd);
			
			SG_API
			void setIPCore(XCOInstr& xcoInstr, IPCore& ipcore);
			
			SG_API 
			void gatherParamsFromMask(Sysgen::BlockDescriptor& bd, Sysgen::IP::IPCore& ipcore);

			SG_API 
			bool isPipelineRegisterUsed(const Sysgen::BlockDescriptor& bd);

                        SG_API 
                        std::string getBlockIPName(const Sysgen::BlockDescriptor& bd);

                        SG_API 
                        std::string getBlockIPVersion(const Sysgen::BlockDescriptor& bd);

			SG_API 
			std::string gatherParamsForBlackBox(Sysgen::BlockDescriptor& bd, Sysgen::IP::HDLBlackBox& blackbox);

                        SG_API
                        bool isClockPort(const AnyTable::AnyType& port);

                        SG_API
                        bool isResetPort(Sysgen::BlockDescriptor& bd, const std::string& port_name,
                               const AnyTable::AnyType& portTranslationMapDict, const std::string& rst);

                        ///returns true, if trim_axipin_name checkbox has been selected on a block's GUI
                        SG_API
                        bool trimAXIPortNames(Sysgen::BlockDescriptor& bd);

                        ///adds in or out ports to the block - if trim_axipin_name checkbox has been
                        ///selected on the block's GUI, it adds ports with a trimmed-down name as the
                        ///simulink token-text ("s_axis_" or "m_axis_" prefix, if any, of the pin names
                        ///are trimmed)
                        SG_API
                        void addPortsToBlock(Sysgen::BlockDescriptor& bd, const std::string& port_name,
                                             const std::string& port_direction, const int channelId);

                        /// recursive method - adds in/out port, for each leaf-level subFieldInfo,
                        /// of an AXI-IP, depending on the 'visibility' of the port w.r.t. sysgen.
                        ///
                        ///     @param subFieldInfo subFieldInfo list for the tdata_info port of
                        ///                         of an AXI-IP; it can be a list of lists i.e.
                        ///                         each subfield, can in turn conatin another list
                        ///                         of its own subfields and so on.
                        ///     @param sysgenPortConfig "ipcore_port_config" data as passed by the 
                        ///                             block's gui.xml - 'visibility' status set
                        ///                             for each port, will determine, if the port
                        ///     @param tdataPortName name of the AXI tdataPort, this is entered into
                        ///                          the block's anytable, for the first subfield port
                        ///                          that gets created. During updatePortInfo, this
                        ///                          information can be used to know the parent tdataport
                        ///                          name, and its tdataInfo
                        ///     @param tdataPortEntered this is a boolean var, just to keep track whether
                        ///                             parent tdataport info has been placed in the block's
                        ///                             anytable or not
                        ///
                        SG_API
                        void addPortsForAXISubFieldInfo(Sysgen::BlockDescriptor& bd,
                                AnyTable::AnyType& subFieldInfo, bool port_visible_default,
                                const std::string& direction, const std::string& tdataPortName,
                                bool& tdataPortEntered, const std::string& port_direction,
                                const int channelId, const AnyTable::AnyType& sysgenPortConfig);

                        SG_API
                        std::string getAXIportGroupPrefix(const std::string& port_name);

			/// return the number ports added, -1 means there is an error
			SG_API 
			int addPorts(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& port_info,
				const std::string& direction = "", 
				bool reportError = false,
				const AnyTable::AnyType& sysgenPortConfig = AnyTable::Dictionary(),
				const std::string& rst = "rst", 
				const std::string& en = "en");

                        SG_API
                        void addPortInfoForMergedDivider(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& ipcorePortInfo);

			SG_API
			bool isClockEnable(const AnyTable::AnyType& port);
			SG_API
			bool isSelectedIndividualClockEnable(const std::string& port_name, Sysgen::BlockDescriptor& bd);
			SG_API
			bool isreset(const AnyTable::AnyType& port);
			/// copy port_config from the mask
	        SG_API 
		    void initPortConfig(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& portConfig);

                SG_API
                void configureTypeIfFixedPtDivgenFractional(Sysgen::BlockDescriptor& bd,
                         const std::string& port_name, Sysgen::SysgenType& port_sysgen_type);

                /// sets type for one block-output port
                SG_API
                void configureTypeForOneOutport(Sysgen::BlockDescriptor& bd,
                                 AnyTable::AnyType& port, const std::string& port_name,
                                 int ipcore_verbose, bool allowArbitraryBinaryPoint,
                                 AnyTable::AnyType& sysgenPortConfig, bool isAXItdataSubFieldPort = false);

                /// recursive method - sets type for each leaf-level subfield (for which ports have
                /// been created in the block) of the AXI-tdata output port
                SG_API
                void configureTypeForAXISubFieldInfo(Sysgen::BlockDescriptor& bd,
                              AnyTable::AnyType& subFieldInfo, int ipcore_verbose,
                              bool allowArbitraryBinaryPoint, AnyTable::AnyType& sysgenPortConfig);

		    SG_API 
			void configureOutType(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& ipcorePortInfo, 
                                  AnyTable::AnyType& sysgenPortConfig);

                SG_API
                void configureOutRateAndTypeForMergedDivider(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& ipcorePortInfo);

                SG_API
                void configureRateAndTypeForMergedDividerOut(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& subFieldInfo);

                SG_API
                void configureRateAndTypeForDivOut(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& port,
                                                   const std::string& port_name, int ipcore_verbose);

	        SG_API 
		    void extractSysgenType(const AnyTable::AnyType& port, 
                                   Sysgen::SysgenType::ArithmeticType& type,
                                   int& width,
                                   int& binpt);
            
            /// utility to derive port type from port_info of IPCore and portConfig of mask
            SG_API 
            Sysgen::SysgenType derivePortSysgenType(const AnyTable::AnyType& port, const AnyTable::AnyType& sysgenOnePortConfig, bool allowArbitraryBinaryPoint = false);

            /// utility to extract type info for AXI tdata-subfield ports.
            SG_API
            void extractAXItdataSubFieldSysgenType(const AnyTable::AnyType& port, 
                                        Sysgen::SysgenType::ArithmeticType& type,
                                        int& width, int& binpt);

            /// utility to derive port type from port_info of IPCore and portConfig of mask,
            /// for ports, corresponding to leaf-level subfields of AXI-tdata pin
            SG_API
            Sysgen::SysgenType deriveAXItdataSubFieldPortSysgenType(const AnyTable::AnyType& port,
                                                     const AnyTable::AnyType& sysgenOnePortConfig,
                                                     bool allowArbitraryBinaryPoint = false);

            /// utility to extract period information for AXI tdata subField
            SG_API
            double deriveAXItdataSubFieldPortSysgenPeriod(const AnyTable::AnyType& port,
                                           const AnyTable::AnyType& sysgenOnePortConfig,
                                                                 double default_period);

            /// recursive method to propagate rate information for output ports, corresponding
            /// to AXI tdata subfields.
            SG_API
            void configureRateForAXISubFieldInfo(Sysgen::BlockDescriptor& bd,
                                             AnyTable::AnyType& subFieldInfo,
                                const AnyTable::AnyType& sysgenOnePortConfig,
                                   double default_period, bool force_period);

            SG_API 
            void configureOutRate(Sysgen::BlockDescriptor& bd, AnyTable::AnyType& ipcorePortInfo, 
                                  AnyTable::AnyType& sysgenPortConfig);

            /// utility to derive port period from port_info of IPCore and portconfig of mask
            SG_API
            double derivePortSysgenPeriod(const AnyTable::AnyType& port, const AnyTable::AnyType& sysgenOnePortConfig, double default_period = 1.0);

            SG_API
            void configureBoolHDLPorts(Sysgen::BlockDescriptor& bd, 
                                       const AnyTable::AnyType& ipcorePortInfo, 
                                       const AnyTable::AnyType& sysgenPortConfig);
            /// if there is any hdl only port, the anytable should look like
            /// config["portname"]["hdltie"] = value;
            /// value can be 1 or 0 or "GND" or "VCC" or "HIGH_IMPEDENCE" 
            /// or "PIN" or "CE" or "N_HDLTIE"
            SG_API
            void configureHDLOnlyPorts(Sysgen::BlockDescriptor& bd,
                                       const AnyTable::AnyType& ipcorePortInfo,
                                       const AnyTable::AnyType& sysgenPortConfig);

            /// this is a utility function used by configureHDLOnlyPorts
            /// if exists portInfo["portname"]["hdltie"]
            /// then hdltie["portname"] = portInfo["portname"]["hdltie"];
            SG_API
            void collectHDLTie(AnyTable::AnyType& hdltie,
                               const AnyTable::AnyType& portInfo);

            SG_API
            void checkIfFixedPtDivgenTdataPort(const Sysgen::BlockDescriptor& bd,
                        const std::string& port_name, Sysgen::SysgenType& port_sysgen_type);

            /// utility to verify type information for one input port of a block.
            SG_API
            void checkOneInportType(const Sysgen::BlockDescriptor& bd,
                                    const AnyTable::AnyType& port,
                                    const std::string& port_name,
                                    const AnyTable::AnyType& sysgenPortConfig,
                                    bool isAXItdataSubFieldPort = false);

            /// recursive method - invokes checkOneInportType to verify type
            /// information for each leaf-level subfield (corresponding to which
            /// one input port has been create in the block) of AXI tdata pin
            SG_API
            void checkInTypeForAXISubFieldInfo(const Sysgen::BlockDescriptor& bd,
                                               const AnyTable::AnyType& subFieldInfo,
                                               const AnyTable::AnyType& sysgenPortConfig,
                                               bool merged_divider_block,
                                               const std::string& tdataPortName);

            SG_API 
            void checkInType(const Sysgen::BlockDescriptor& bd, 
                             const AnyTable::AnyType& ipcorePortInfo, 
                             const AnyTable::AnyType& sysgenPortConfig,
                             const std::string& rst = "rst",
                             const std::string& en = "en");

            // we should add arguments to indicate whether we want to include
            // rst or en to the block port list
            // or maybe we should add an argument for default port values
            SG_API 
            std::string entityPortListString(Sysgen::BlockDescriptor& bd, 
                                             int leadingSpaces = 4, 
                                             const std::string& hdl = "vhdl");

            SG_API 
            std::string componentPortListString(const Sysgen::IP::IPCore& ipcore, 
                                                int leadingSpaces = 4, 
                                                const std::string& hdl = "vhdl");

            SG_API
            std::string instancePortMapString(const Sysgen::IP::IPCore& ipcore, 
                                              int leadingSpaces = 4, 
                                              const AnyTable::AnyType& override = AnyTable::AnyType(), 
                                              const std::string& hdl = "vhdl");

            /**
             * return empty if is supported, otherwise, return the error message
             */
            SG_API
            std::string checkDeviceFamilyIsSupported(const Sysgen::BlockDescriptor& bd,
                                                     const Sysgen::IP::IPCore& ipcore, 
                                                     bool add_error = true);

            /// creates one Sequence record {name arith width binpt latency rate}
            /// for a port of the block
            SG_API
            AnyTable::Sequence buildOnePortInfo(const std::string& portName,
                                     const Sysgen::SysgenType& portType, double rate);

            /// creates one sequence record for an AXI tdata-subfield, which is composite
            /// i.e. the record looks like {name type width actualwidth arith binpt subFieldInfoList}
            SG_API
            AnyTable::Sequence buildOneCompositeSubFieldInfo(const AnyTable::AnyType& subField,
                                                             const Sysgen::SysgenType& portType,
                                                             double rate);

            /// creates one sequence record for an atomic subfield of AXI tdata pin - i.e. the
            /// record looks like {name type width actualwidth arith binpt}
            SG_API
            AnyTable::Sequence buildOneAtomicSubFieldInfo(const AnyTable::AnyType& subField,
                                    const std::string& portName, const Sysgen::SysgenType& portType,
                                    double rate);

            /// recursive method - creates sequence record recursively for each leaf-level subfield
            /// and appends accordingly to a list, to create the subFieldInfoList of an AXI tdata pin
            SG_API
            void buildAXItdataSubFieldInfo(const AnyTable::AnyType& subFieldInfo,
                             Sysgen::BlockDescriptor::ConstPortIterator& portIter,
                             AnyTable::Sequence& AXItdataSubFieldInfoList, int& currIndex,
                             bool& incrPortIter, double default_input_force_period,
                             bool merged_divider_block);

            /// creates one sequence record for AXI tdata-info - the record looks like
            /// {axiClassName dataClassName interfaceWidth subFieldInfoList}
            SG_API
            AnyTable::Sequence updateAXItdataInfo(const AnyTable::Dictionary& tdataInfo,
                              Sysgen::BlockDescriptor::ConstPortIterator& portIter,
                              double default_input_force_period,
                              bool merged_divider_block);

            SG_API void updatePortInfo(const Sysgen::BlockDescriptor& bd,
                                       const Sysgen::IP::IPCore& ipcore,
                                       const std::string& kind,
                                       AnyTable::AnyType& port_info);

            SG_API void checkIfFixedPointDivision(const Sysgen::BlockDescriptor& bd,
                                                  const Sysgen::IP::IPCore& ipcore,
                                                  int dividend_bp,
                                                  int divisor_bp);

            SG_API void configureRateAndType(Sysgen::BlockDescriptor& bd,
                                             Sysgen::IP::IPCore& ipcore,
											 AnyTable::AnyType& sysgenPortConfig, AnyTable::AnyType& portInfo);

            SG_API void configurePostRateAndType(const Sysgen::BlockDescriptor& bd,
                                                 const Sysgen::IP::IPCore& ipcore,
                                                 const AnyTable::AnyType& sysgenPortConfig);

                                             
            // SG_API void convertParamDes2Params(const AnyTable::AnyType& paramDes,
                                               // AnyTable::AnyType& params);
            SG_API std::string spaces(int n);

            // configure one clock
            SG_API void configureClocks(Sysgen::BlockDescriptor& bd,
                                        Sysgen::IP::IPCore& ipcore);

            // configure one clock
            SG_API void configureClocks(Sysgen::BlockDescriptor& bd,
                                        const AnyTable::AnyType& port_list);

            //configure clock ce pair if available in the core
            SG_API void configureClkCEPair(Sysgen::BlockDescriptor& bd,
				Sysgen::IP::IPCore& ipcore, 
				const AnyTable::AnyType& updatedPortInfo);

            SG_API void configureNetlist(Sysgen::BlockDescriptor& bd, 
                                         const std::string& hdl_template,
                                         bool use_ngc_netlist_for_verilog,
                                         Sysgen::IP::IPCore& ipcore,
                                         const std::map<std::string, std::string>& custom_mapping,
                                         bool structural_sim = false);
            
            /// utility to configure blackbox netlist
            SG_API void blackboxConfigureNetlist(Sysgen::BlockDescriptor& bd, 
                                                 const Sysgen::IP::HDLBlackBox& blackbox,
                                                 const AnyTable::AnyType& port_config);
            
            //Using this to gate previous behavior of coregenImportBlock
            SG_API bool isWrapperAvailable(const Sysgen::BlockDescriptor& bd);

            SG_API bool isWrapperOnlyPort(const Sysgen::PortDescriptor& pd);

            SG_API double propagateSysgenIPRate(Sysgen::BlockDescriptor& bd);

            SG_API std::string arrayToCoeFile(const std::vector<double>& coefficients, int binpt);

            SG_API void checkInRate(const Sysgen::BlockDescriptor& bd, const AnyTable::AnyType& ipcorePortInfo, const AnyTable::AnyType& sysgenPortConfig);

            SG_API void checkAresetnRate(const Sysgen::BlockDescriptor& bd, const std::string& rst = "rst");

            SG_API bool isRunCoreAtSystemPeriod(const BlockDescriptor& bd);

            SG_API bool isAllowArbitraryBinaryPoint(const AnyTable::AnyType& blockAnyTable);
            
            SG_API bool needToForcePeriod(const BlockDescriptor& bd);
            
            SG_API double getDefaultInputForcePeriod(const BlockDescriptor& bd);

            SG_API std::string getResetParamName(const BlockDescriptor& bd);

			SG_API std::string getClockEnableParamName(const BlockDescriptor& bd);

			SG_API std::string getIndividualClockEnableParamName(const std::string& port_name);

			SG_API bool getClockEnableRequest(const BlockDescriptor& bd);

			SG_API bool getIndividualClockEnableRequest(const BlockDescriptor& bd);

			SG_API void unPackPortTranslationMap(const BlockDescriptor& bd, AnyTable::AnyType& a);

			SG_API void overrideClockEnableRequest(BlockDescriptor& bd, bool o);

			SG_API bool isDSPToolReady(const BlockDescriptor& bd);

			SG_API std::string getCEName(double period, bool minPeriod, bool ce_logic = false);

			SG_API std::string getClkName(double period, bool minPeriod, bool ce_logic = false);

			//This function is used to convert generics obtained from coregen api into generic map instructions
			SG_API std::string convertGenericsToTemplateInstruction(const BlockDescriptor& bd);

			SG_API void normalizeExplicitPeriodMaskParam(BlockDescriptor& bd);


        }
    }
}
#endif
