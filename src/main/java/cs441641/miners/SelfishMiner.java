package cs441641.miners;

import cs441641.blockchain.Block;
import cs441641.blockchain.NetworkStatistics;

public class SelfishMiner extends BaseMiner implements Miner {

	private Block secretBlock;
	private Block currentHead;
	private double totalHashRate;
	public SelfishMiner(String id, int hashRate, int connectivity) {
		super(id, hashRate, connectivity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Block currentlyMiningAt() {
		// TODO Auto-generated method stub
		return secretBlock;
	}

	@Override
	public Block currentHead() {
		// TODO Auto-generated method stub
		return currentHead;
	}

	@Override
	public void blockMined(Block block, boolean isMinerMe) {
		// TODO Auto-generated method stub
		if (this.currentHead == null || this.secretBlock == null) {
			this.currentHead = block;
			this.secretBlock = block;
		}
		if (isMinerMe && currentHead.getHeight() - 1 < this.secretBlock.getHeight()) {
			this.secretBlock = block;
		} else if (isMinerMe || (!isMinerMe && block.getHeight() >= this.secretBlock.getHeight())) {
			this.secretBlock = currentHead;
			this.currentHead = block;
		} else {
			this.currentHead = this.secretBlock;
		}
	}

	@Override
	public void networkUpdate(NetworkStatistics statistics) {
		this.totalHashRate = statistics.getTotalHashRate();
	}

	@Override
	public void initialize(Block genesis, NetworkStatistics statistics) {
		this.currentHead = genesis;
		this.secretBlock = genesis;
		this.totalHashRate = statistics.getTotalHashRate();

	}

}
