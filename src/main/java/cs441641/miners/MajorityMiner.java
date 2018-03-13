package cs441641.miners;

import cs441641.blockchain.Block;
import cs441641.blockchain.NetworkStatistics;

public class MajorityMiner extends BaseMiner implements Miner {

	private Block currentHead;
	private double totalHashRate;
	public MajorityMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
		// TODO Auto-generated constructor stub
	}
	
	private double getHashPercentage() { 
		return this.getHashRate() / this.totalHashRate;
	}

	@Override
	public Block currentlyMiningAt() {
		// TODO Auto-generated method stub
		return currentHead;
	}

	@Override
	public Block currentHead() {
		// TODO Auto-generated method stub
		return currentHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {

		if ( this.getHashPercentage() >= 0.51 && isMinerMe ) {
			this.currentHead = block;
		} else if ( this.getHashPercentage() >= 0.51 ){
			return;
		} else {
			if ( this.currentHead == null ) {
				this.currentHead = block;
			} else if ( block.getHeight() > this.currentHead.getHeight() ) {
				this.currentHead = block;
			}
		}
		
//		if (isMinerMe) {
//			if (block.getHeight() > currentHead.getHeight()) {
//				this.currentHead = block;
//			}
//		
//		} else if (this.getHashPercentage() < 0.50) {
//			if (currentHead == null) {
//				currentHead = block;
//			} else if (block.getHeight() > currentHead.getHeight() ) {
//				this.currentHead = block;
//			}
//		} else if (currentHead.getHeight() < block.getHeight()) {
//			currentHead = block.getPreviousBlock();
//		} 

	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		this.totalHashRate = statistics.getTotalHashRate();

	}

	@Override
	public void initialize(Block genesis, NetworkStatistics statistics) {
		this.currentHead = genesis;
		this.totalHashRate = statistics.getTotalHashRate();
	}

}
