from mcresources import ResourceManager
from constants import *


def generate(rm: ResourceManager):
    rm.item_tag('curios:back', '#tfc:barrels', 'tfc:ceramic/large_vessel', *['tfc:ceramic/large_vessel/%s' % c for c in COLORS], '#tfc:anvils')
